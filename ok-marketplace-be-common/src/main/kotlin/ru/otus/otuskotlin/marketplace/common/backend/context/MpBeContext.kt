package ru.otus.otuskotlin.marketplace.common.backend.context

import ru.otus.otuskotlin.marketplace.common.backend.models.*

data class MpBeContext(
    var status: MpBeContextStatus = MpBeContextStatus.NONE,
    var errors: MutableList<IMpError> = mutableListOf(),
    var stubCase: MpStubCase = MpStubCase.NONE,

    var requestDemandId: MpDemandIdModel = MpDemandIdModel.NONE,
    var requestProposalId: MpProposalIdModel = MpProposalIdModel.NONE,
    var requestDemand: MpDemandModel = MpDemandModel.NONE,
    var requestProposal: MpProposalModel = MpProposalModel.NONE,
    var demandFilter: MpDemandFilterModel = MpDemandFilterModel.NONE,
    var proposalFilter: MpProposalFilterModel = MpProposalFilterModel.NONE,

    var responseDemand: MpDemandModel = MpDemandModel.NONE,
    var responseProposal: MpProposalModel = MpProposalModel.NONE,
    var responseDemands: MutableList<MpDemandModel> = mutableListOf(),
    var responseProposals: MutableList<MpProposalModel> = mutableListOf(),
)
