package services

import items.demands.update.MarketplaceDemandUpdate
import items.demands.view.MarketplaceDemandView
import models.*
import responded
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechDetsDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechParamDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.UnitTypeDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpDemandDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpResponseDemandList
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpResponseDemandOffersList
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpResponseDemandRead

class MarketplaceDemandService(
    private val tagService: MarketplaceTagService,
) {
    suspend fun get(id: DemandIdModel?): DemandModel? = MpFrontContext().run {
        id ?: return null
        val response = mockRead(id.asString())
        responded(response)
        demand.processDemand()
    }

    suspend fun find(filterModel: DemandFilterModel): ResponseDemandsModel = MpFrontContext().run {
        val response = mockList()
        ResponseDemandsModel(
            demands = responded(response)
                .demands
                .onEach { it.processDemand() }
                .toMutableList()
        )
    }

    suspend fun demandOffers(id: DemandIdModel) = MpFrontContext().run {
        val ressponse = mockOffers(id)
        responded(ressponse)
        ResponseDemandOffersModel(
            demandId = id,
            proposals = demandOffers
        )
    }

    private suspend fun DemandModel.processDemand() = apply {
        updateLinks()
        updateTags()
    }

    private suspend fun DemandModel.updateTags() = apply {
        tags = tagService.resolveTags(tagIds).toMutableSet()
    }

    private suspend fun DemandModel.updateLinks() = apply {
        linkView = MarketplaceDemandView.makeLink(id)
        linkEdit = MarketplaceDemandUpdate.makeLink(id)
        linkDelete = ""
    }

    companion object {
        private fun mockList() = MpResponseDemandList(
            responseId = "123",
            onRequest = "321",
            status = ResponseStatusDto.SUCCESS,
            demands = listOf(
                mockRead("demand-001").demand!!,
                mockRead("demand-002").demand!!,
                mockRead("demand-003").demand!!,
                mockRead("demand-004").demand!!,
                mockRead("demand-005").demand!!,
                mockRead("demand-006").demand!!,
            )
        )

        fun mockOffers(id: DemandIdModel): MpResponseDemandOffersList = MpResponseDemandOffersList(
            responseId = "123",
            onRequest = "321",
            status = ResponseStatusDto.SUCCESS,
            demandProposals = listOf(
                MarketplaceProposalService.mockRead("proposal-001").proposal!!,
                MarketplaceProposalService.mockRead("proposal-002").proposal!!,
                MarketplaceProposalService.mockRead("proposal-003").proposal!!,
                MarketplaceProposalService.mockRead("proposal-004").proposal!!,
                MarketplaceProposalService.mockRead("proposal-005").proposal!!,
                MarketplaceProposalService.mockRead("proposal-006").proposal!!,
            )
        )

        fun mockRead(id: String) = MpResponseDemandRead(
            responseId = "123",
            onRequest = "321",
            status = ResponseStatusDto.SUCCESS,
            demand = MpDemandDto(
                id = id,
                avatar = "imgs/converter.jpeg",
                title = "Demand $id",
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
