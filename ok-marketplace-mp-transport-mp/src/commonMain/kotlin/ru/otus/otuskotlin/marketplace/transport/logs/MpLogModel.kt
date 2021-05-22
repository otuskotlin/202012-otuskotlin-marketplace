package ru.otus.otuskotlin.marketplace.transport.logs

import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpDemandDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpProposalDto

data class MpLogModel(
    val requestDemandId: String? = null,
    val requestProposalId: String? = null,
    val requestDemand: MpDemandDto? = null,
    val requestProposal: MpProposalDto? = null,
    val responseDemand: MpDemandDto? = null,
    val responseProposal: MpProposalDto? = null,
    val responseDemands: List<MpDemandDto>? = null,
    val responseProposals: List<MpProposalDto>? = null,
)
