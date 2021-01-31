package services

import models.*

class MarketplaceBackendService {

    suspend inline fun demand(id: String?) = id?.let { demand(DemandIdModel(it)) }
    suspend fun demand(id: DemandIdModel?): DemandModel? {
        id ?: return null
        return DemandModel(
            id = id,
            avatar = "imgs/converter.jpeg",
        )
    }

    suspend inline fun proposal(id: String?) = id?.let { proposal(ProposalIdModel(it)) }
    suspend fun proposal(id: ProposalIdModel?): ProposalModel? {
        id ?: return null
        return ProposalModel(
            id = id,
            avatar = "imgs/converter.jpeg",
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
