package models

data class ResponseDemandOffersModel(
    var demandId: DemandIdModel,
    var proposals: List<ProposalModel>
) {

}
