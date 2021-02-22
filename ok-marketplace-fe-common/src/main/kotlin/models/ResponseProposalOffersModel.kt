package models

data class ResponseProposalOffersModel(
    var proposalId: models.ProposalIdModel,
    var demands: List<models.DemandModel>
) {

}
