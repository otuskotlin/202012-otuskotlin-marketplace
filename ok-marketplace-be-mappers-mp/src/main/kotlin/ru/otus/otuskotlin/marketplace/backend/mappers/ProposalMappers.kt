package ru.otus.otuskotlin.marketplace.backend.mappers

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*

fun MpBeContext.setQuery(query: MpRequestProposalRead) = apply {
    requestProposalId = query.proposalId?.let { MpProposalIdModel(it) }?: MpProposalIdModel.NONE
}

fun MpBeContext.setQuery(query: MpRequestProposalCreate) = apply {
    requestProposal = query.createData?.toModel()?: MpProposalModel.NONE
}

fun MpBeContext.setQuery(query: MpRequestProposalUpdate) = apply {
    requestProposal = query.updateData?.toModel()?: MpProposalModel.NONE
}

fun MpBeContext.setQuery(query: MpRequestProposalDelete) = apply {
    requestProposalId = query.proposalId?.let { MpProposalIdModel(it) }?: MpProposalIdModel.NONE
}

fun MpBeContext.setQuery(query: MpRequestProposalList) = apply {
    proposalFilter = query.filterData?.let {
        MpProposalFilterModel(
            text = it.text?: ""
        )
    }?: MpProposalFilterModel.NONE
}

fun MpBeContext.setQuery(query: MpRequestProposalOffersList) = apply {
    requestProposalId = query.proposalId?.let { MpProposalIdModel(it) }?: MpProposalIdModel.NONE
}

fun MpBeContext.respondProposalGet() = MpResponseProposalRead(
    proposal = responseProposal.takeIf { it != MpProposalModel.NONE }?.toTransport()
)

fun MpBeContext.respondProposalCreate() = MpResponseProposalCreate(
    proposal = responseProposal.takeIf { it != MpProposalModel.NONE }?.toTransport()
)

fun MpBeContext.respondProposalUpdate() = MpResponseProposalUpdate(
    proposal = responseProposal.takeIf { it != MpProposalModel.NONE }?.toTransport()
)

fun MpBeContext.respondProposalDelete() = MpResponseProposalDelete(
    proposal = responseProposal.takeIf { it != MpProposalModel.NONE }?.toTransport()
)

fun MpBeContext.respondProposalList() = MpResponseProposalList(
    proposals = responseProposals.takeIf { it.isNotEmpty() }?.filter { it != MpProposalModel.NONE }
        ?.map { it.toTransport() }
)

fun MpBeContext.respondProposalOffers() = MpResponseProposalOffersList(
     proposalDemands = responseDemands.takeIf { it.isNotEmpty() }?.filter { it != MpDemandModel.NONE}
        ?.map { it.toTransport() }
)

private fun MpProposalUpdateDto.toModel() = MpProposalModel(
    id = id?.let { MpProposalIdModel(it) }?: MpProposalIdModel.NONE,
    avatar = avatar?: "",
    title = title?: "",
    description = description?: "",
    tagIds = tagIds?.toMutableSet()?: mutableSetOf(),
    techDets = techDets?.map { it.toModel() }?.toMutableSet()?: mutableSetOf()
)

private fun MpProposalCreateDto.toModel() = MpProposalModel(
    avatar = avatar?: "",
    title = title?: "",
    description = description?: "",
    tagIds = tagIds?.toMutableSet()?: mutableSetOf(),
    techDets = techDets?.map { it.toModel() }?.toMutableSet()?: mutableSetOf()
)
