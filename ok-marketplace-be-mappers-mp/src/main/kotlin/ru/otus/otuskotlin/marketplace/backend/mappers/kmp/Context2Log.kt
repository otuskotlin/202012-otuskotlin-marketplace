package ru.otus.otuskotlin.marketplace.backend.mappers.kmp

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel
import ru.otus.otuskotlin.marketplace.transport.logs.CommonLogModel
import ru.otus.otuskotlin.marketplace.transport.logs.MpLogModel
import java.time.Instant
import java.util.*

fun MpBeContext.toLog(logId: String) = CommonLogModel(
    messageId = UUID.randomUUID().toString(),
    messageTime = Instant.now().toString(),
    source = "ok-marketplace",
    logId = logId,
    marketplace = MpLogModel(
        requestDemandId = requestDemandId.takeIf { it != MpDemandIdModel.NONE }?.asString(),
        requestProposalId = requestProposalId.takeIf { it != MpProposalIdModel.NONE }?.asString(),
        requestDemand = requestDemand.takeIf { it != MpDemandModel.NONE }?.toTransport(),
        requestProposal = responseProposal.takeIf { it != MpProposalModel.NONE }?.toTransport(),
        responseDemand = responseDemand.takeIf { it != MpDemandModel.NONE }?.toTransport(),
        responseProposal = responseProposal.takeIf { it != MpProposalModel.NONE }?.toTransport(),
        responseDemands = responseDemands.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
        responseProposals = responseProposals.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    ),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
)
