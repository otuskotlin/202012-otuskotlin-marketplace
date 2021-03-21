package services

import items.proposals.update.MarketplaceProposalUpdate
import items.proposals.view.MarketplaceProposalView
import models.*
import responded
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechDetsDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechParamDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.UnitTypeDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpProposalDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpResponseProposalList
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpResponseProposalOffers
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpResponseProposalRead

class MarketplaceProposalService(
    private val tagService: MarketplaceTagService,
) {
    suspend fun get(id: ProposalIdModel?): ProposalModel? = MpFrontContext().run {
        id ?: return null
        val response = mockRead(id.asString())
        responded(response)
        proposal.processProposal()
    }

    suspend fun find(filterModel: ProposalFilterModel): ResponseProposalsModel = MpFrontContext().run {
        val response = mockList()
        ResponseProposalsModel(
            proposals = responded(response)
                .proposals
                .onEach { it.processProposal() }
                .toMutableList()
        )
    }

    suspend fun proposalOffers(id: ProposalIdModel) = MpFrontContext().run {
        val ressponse = mockOffers(id)
        responded(ressponse)
        ResponseProposalOffersModel(
            proposalId = id,
            demands = proposalOffers
        )
    }

    private suspend fun ProposalModel.processProposal() = apply {
        updateLinks()
        updateTags()
    }

    private suspend fun ProposalModel.updateTags() = apply {
        tags = tagService.resolveTags(tagIds).toMutableSet()
    }

    private suspend fun ProposalModel.updateLinks() = apply {
        linkView = MarketplaceProposalView.makeLink(id)
        linkEdit = MarketplaceProposalUpdate.makeLink(id)
        linkDelete = ""
    }

    companion object {
        private fun mockList() = MpResponseProposalList(
            responseId = "123",
            onRequest = "321",
            status = ResponseStatusDto.SUCCESS,
            proposals = listOf(
                mockRead("proposal-001").proposal!!,
                mockRead("proposal-002").proposal!!,
                mockRead("proposal-003").proposal!!,
                mockRead("proposal-004").proposal!!,
                mockRead("proposal-005").proposal!!,
                mockRead("proposal-006").proposal!!,
            )
        )

        fun mockOffers(id: ProposalIdModel): MpResponseProposalOffers = MpResponseProposalOffers(
            responseId = "123",
            onRequest = "321",
            status = ResponseStatusDto.SUCCESS,
            proposalDemands = listOf(
                MarketplaceDemandService.mockRead("demand-001").demand!!,
                MarketplaceDemandService.mockRead("demand-002").demand!!,
                MarketplaceDemandService.mockRead("demand-003").demand!!,
                MarketplaceDemandService.mockRead("demand-004").demand!!,
                MarketplaceDemandService.mockRead("demand-005").demand!!,
                MarketplaceDemandService.mockRead("demand-006").demand!!,
            )
        )

        fun mockRead(id: String) = MpResponseProposalRead(
            responseId = "123",
            onRequest = "321",
            status = ResponseStatusDto.SUCCESS,
            proposal = MpProposalDto(
                id = id,
                avatar = "imgs/converter.jpeg",
                title = "Proposal $id",
                description = """
                <p>Требуется товар $id. Требуемая спецификация представлена ниже.</p>
                <p>Будем использовать самостоятельно</p>
            """.trimIndent(),
                techDets = setOf(
                    TechDetsDto(
                        id = "td-001",
                        param = TechParamDto(
                            id = "tp-001",
                            name = "Полная масса",
                            description = "Масса товара, исключая упаковку",
                            priority = 1000.0,
                            units = mockUnits()
                        )
                    ),
                    TechDetsDto(
                        id = "td-002",
                        param = TechParamDto(
                            id = "tp-001",
                            name = "Сухая масса",
                            description = "Масса товара, исключая горючее и другие расходные материалы",
                            priority = 1000.0,
                            units = mockUnits()
                        )
                    ),
                    TechDetsDto(
                        id = "td-003",
                        param = TechParamDto(
                            id = "tp-001",
                            name = "Масса НЕТТО",
                            description = "Масса товара, исключая упаковку",
                            priority = 1000.0,
                            units = mockUnits()
                        )
                    ),
                    TechDetsDto(
                        id = "td-004",
                        param = TechParamDto(
                            id = "tp-001",
                            name = "Масса БРУТТО",
                            description = "Масса товара, исключая упаковку",
                            priority = 1000.0,
                            units = mockUnits()
                        )
                    ),
                ),
            )
        )

        fun mockUnits() = setOf(
            UnitTypeDto(id = "ut-001", name = "Килограмм", symbol = "кг")
        )
    }
}
