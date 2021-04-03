package ru.otus.otuskotlin.marketplace.common.backend.context

import ru.otus.otuskotlin.marketplace.common.backend.models.*
import ru.otus.otuskotlin.marketplace.common.backend.repositories.EmptyUserSession
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IUserSession
import java.time.Instant

data class MpBeContext(
    var timeStarted: Instant = Instant.MIN,
    var responseId: String = "",
    var onRequest: String = "",
    var status: MpBeContextStatus = MpBeContextStatus.NONE,
    var errors: MutableList<IMpError> = mutableListOf(),
    var frameworkErrors: MutableList<Throwable> = mutableListOf(),
    var stubCase: MpStubCase = MpStubCase.NONE,

    val userSession: IUserSession<*> = EmptyUserSession,

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
