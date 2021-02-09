package ru.otus.otuskotlin.marketplace.common.backend.context

import ru.otus.otuskotlin.marketplace.backend.common.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.backend.common.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.backend.common.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.backend.common.models.MpProposalModel

data class MpBeContext(
    var requestDemandId: MpDemandIdModel = MpDemandIdModel.NONE,
    var requestProposalId: MpProposalIdModel = MpProposalIdModel.NONE,
    var requestDemand: MpDemandModel = MpDemandModel.NONE,
    var requestProposal: MpProposalModel = MpProposalModel.NONE,

    var responseDemand: MpDemandModel = MpDemandModel.NONE,
    var responseProposal: MpProposalModel = MpProposalModel.NONE,
)
