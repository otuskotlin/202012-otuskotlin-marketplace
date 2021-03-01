package services

import models.*

class MarketplaceBackendService {
    private val tagService = MarketplaceTagService()
    private val demandService = MarketplaceDemandService(tagService = tagService)
    private val proposalService = MarketplaceProposalService(tagService = tagService)

    suspend inline fun demand(id: String?) = id?.let { demand(DemandIdModel(it)) }
    suspend fun demand(id: DemandIdModel?): DemandModel? = demandService.get(id)
    suspend fun demands(filter: DemandFilterModel): ResponseDemandsModel = demandService.find(filter)
    suspend fun demandOffers(id: DemandIdModel): ResponseDemandOffersModel = demandService.demandOffers(id)

    suspend inline fun proposal(id: String?) = id?.let { proposal(ProposalIdModel(it)) }
    suspend fun proposal(id: ProposalIdModel?): ProposalModel? = proposalService.get(id)
    suspend fun proposals(filter: ProposalFilterModel): ResponseProposalsModel = proposalService.find(filter)
    suspend fun proposalOffers(id: ProposalIdModel): ResponseProposalOffersModel = proposalService.proposalOffers(id)
}
