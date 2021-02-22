package models

data class MpFrontContext(
    var demand: DemandModel = DemandModel.NONE,
    var demands: MutableList<DemandModel> = mutableListOf(),
    var demandOffers: MutableList<ProposalModel> = mutableListOf(),
    var proposal: ProposalModel = ProposalModel.NONE,
    var proposals: MutableList<ProposalModel> = mutableListOf(),
    var proposalOffers: MutableList<DemandModel> = mutableListOf(),
)
