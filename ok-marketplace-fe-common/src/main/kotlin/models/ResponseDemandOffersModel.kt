package models

data class ResponseDemandOffersModel(
    var demandId: models.DemandIdModel,
    var proposals: List<models.ProposalModel>
) {

}
