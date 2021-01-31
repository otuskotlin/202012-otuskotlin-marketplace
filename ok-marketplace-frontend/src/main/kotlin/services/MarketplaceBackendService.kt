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
            linkView = MarketplaceDemandView.makeLink(id),
            linkEdit = MarketplaceDemandUpdate.makeLink(id),
            linkDelete = "",
        )
    }

    suspend inline fun proposal(id: String?) = id?.let { proposal(ProposalIdModel(it)) }
    suspend fun proposal(id: ProposalIdModel?): ProposalModel? {
        id ?: return null
        return ProposalModel(
            id = id,
            avatar = "imgs/converter.jpeg",
            title = "Proposal $id",
            linkView = MarketplaceProposalView.makeLink(id),
            linkEdit = MarketplaceProposalUpdate.makeLink(id),
            linkDelete = "",
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
        demands = listOf(
            proposal(id = ProposalIdModel("proposal-1"))!!,
            proposal(id = ProposalIdModel("proposal-2"))!!,
            proposal(id = ProposalIdModel("proposal-3"))!!,
            proposal(id = ProposalIdModel("proposal-4"))!!,
        )
    )
}
