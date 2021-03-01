package ru.otus.otuskotlin.marketplace.common.backend.context

import ru.otus.otuskotlin.marketplace.common.backend.models.*

data class MpBeContext(
    var requestDemandId: MpDemandIdModel = MpDemandIdModel.NONE,
    var requestProposalId: MpProposalIdModel = MpProposalIdModel.NONE,
    var requestDemand: MpDemandModel = MpDemandModel.NONE,
    var requestProposal: MpProposalModel = MpProposalModel.NONE,
    var demandFilter: MpDemandFilterModel = MpDemandFilterModel.NONE,
    var proposalFilter: MpProposalFilterModel = MpProposalFilterModel.NONE,

    var responseDemand: MpDemandModel = MpDemandModel.NONE,
    var responseDemands: List<MpDemandModel> = emptyList(),
    var responseProposal: List<MpProposalModel> = emptyList(),
    var responseProposals: MpProposalModel = MpProposalModel.NONE,
)
