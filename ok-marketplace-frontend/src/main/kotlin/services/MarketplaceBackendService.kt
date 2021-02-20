package services

import items.demands.update.MarketplaceDemandUpdate
import items.demands.view.MarketplaceDemandView
import items.proposals.update.MarketplaceProposalUpdate
import items.proposals.view.MarketplaceProposalView
import models.*

class MarketplaceBackendService {

    suspend inline fun demand(id: String?) = id?.let { demand(DemandIdModel(it)) }
    suspend fun demand(id: DemandIdModel?): DemandModel? {
        id ?: return null
        return DemandModel(
            id = id,
            avatar = "imgs/converter.jpeg",
            title = "Demand $id",
            description = """
                <p>Требуется товар $id. Требуемая спецификация представлена ниже.</p>
                <p>Будем использовать самостоятельно</p>
            """.trimIndent(),
            linkView = MarketplaceDemandView.makeLink(id),
            linkEdit = MarketplaceDemandUpdate.makeLink(id),
            linkDelete = "",
            tags = tags,
            techDets = techDets
        )
    }

    suspend inline fun proposal(id: String?) = id?.let { proposal(ProposalIdModel(it)) }
    suspend fun proposal(id: ProposalIdModel?): ProposalModel? {
        id ?: return null
        return ProposalModel(
            id = id,
            avatar = "imgs/converter.jpeg",
            title = "Proposal $id",
            description = """
                <p>Предлагаем товар $id. Технические параметры представлены ниже.</p>
                <p>Допускается перепродажа.</p>
            """.trimIndent(),
            linkView = MarketplaceProposalView.makeLink(id),
            linkEdit = MarketplaceProposalUpdate.makeLink(id),
            linkDelete = "",
            tags = tags,
            techDets = techDets
        )
    }

    suspend fun demands(filter: DemandFilterModel): ResponseDemandsModel = ResponseDemandsModel(
        demands = listOf(
            demand(id = DemandIdModel("demand-1"))!!,
            demand(id = DemandIdModel("demand-2"))!!,
            demand(id = DemandIdModel("demand-3"))!!,
            demand(id = DemandIdModel("demand-4"))!!,
        )
    )

    suspend fun proposals(filter: ProposalFilterModel): ResponseProposalsModel = ResponseProposalsModel(
        proposals = listOf(
            proposal(id = ProposalIdModel("proposal-1"))!!,
            proposal(id = ProposalIdModel("proposal-2"))!!,
            proposal(id = ProposalIdModel("proposal-3"))!!,
            proposal(id = ProposalIdModel("proposal-4"))!!,
        )
    )

    suspend fun demandOffers(id: DemandIdModel): ResponseDemandOffersModel? = ResponseDemandOffersModel(
        demandId = id,
        proposals = listOf(
            proposal(id = ProposalIdModel("proposal-1"))!!,
            proposal(id = ProposalIdModel("proposal-2"))!!,
            proposal(id = ProposalIdModel("proposal-3"))!!,
            proposal(id = ProposalIdModel("proposal-4"))!!,
        )
    )

    suspend fun proposalOffers(id: ProposalIdModel): ResponseProposalOffersModel? = ResponseProposalOffersModel(
        proposalId = id,
        demands = listOf(
            demand(id = DemandIdModel("demand-1"))!!,
            demand(id = DemandIdModel("demand-2"))!!,
            demand(id = DemandIdModel("demand-3"))!!,
            demand(id = DemandIdModel("demand-4"))!!,
        )
    )
}

private val tags = mutableSetOf(
TagModel(TagIdModel("tag-1"), title = "Tag-1", avatar = TagAvatarModel("edit", TagAvatarModel.TagAvatarType.ICON)),
TagModel(TagIdModel("tag-2"), title = "Tag-2", avatar = TagAvatarModel("list", TagAvatarModel.TagAvatarType.ICON)),
TagModel(TagIdModel("tag-3"), title = "Tag-3", avatar = TagAvatarModel("view", TagAvatarModel.TagAvatarType.ICON)),
)

private val unitKg = UnitTypeModel(
    id = UnitTypeIdModel("unit-1"),
    name = "Килограмм",
    symbol = "kg",
    synonyms = mutableSetOf("Kilogram", "Kg", "Кг"),
    symbols = mutableSetOf("kg", "кг")
)

private val techDets = mutableSetOf(
    TechDetModel(
        id = TechDetIdModel("td-1"),
        param = TechParamModel(
            id = TechParamIdModel("tp-1"),
            name = "Минимальная масса",
            description = "Минимальная масса объекта. Собственная масса объекта",
            units = mutableSetOf(unitKg),
        ),
        value = "25",
        unit = unitKg,
        comparableValue = 25_000.0
    ),
    TechDetModel(
        id = TechDetIdModel("td-2"),
        param = TechParamModel(
            id = TechParamIdModel("tp-2"),
            name = "Максимальная масса",
            description = "Максимальная масса объекта. Собственная масса объекта",
            units = mutableSetOf(unitKg),
        ),
        value = "250",
        unit = unitKg,
        comparableValue = 250_000.0
    ),
    TechDetModel(
        id = TechDetIdModel("td-3"),
        param = TechParamModel(
            id = TechParamIdModel("tp-3"),
            name = "Максимальная масса",
            description = "Средняя масса объекта. Собственная масса объекта",
            units = mutableSetOf(unitKg),
        ),
        value = "125",
        unit = unitKg,
        comparableValue = 125_000.0
    ),
    TechDetModel(
        id = TechDetIdModel("td-4"),
        param = TechParamModel(
            id = TechParamIdModel("tp-4"),
            name = "Максимальная масса",
            description = "Нетто масса объекта. Масса объекта вместе с тарой",
            units = mutableSetOf(unitKg),
        ),
        value = "300",
        unit = unitKg,
        comparableValue = 300_000.0
    ),
)
