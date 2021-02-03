package models

data class ResponseProposalOffersModel(
    var proposalId: ProposalIdModel,
    var demands: List<DemandModel>
) {

}
