package ru.otus.otuskotlin.marketplace.backend.mappers.kmp

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*
import java.time.Instant

fun MpBeContext.setQuery(query: MpRequestProposalRead) = setQuery(query) {
    requestProposalId = query.proposalId?.let { MpProposalIdModel(it) }?: MpProposalIdModel.NONE
    stubCase = when (query.debug?.stubCase) {
        MpRequestProposalRead.StubCase.SUCCESS -> MpStubCase.PROPOSAL_READ_SUCCESS
        else -> MpStubCase.NONE
    }
}

fun MpBeContext.setQuery(query: MpRequestProposalCreate) = setQuery(query) {
    requestProposal = query.createData?.toModel()?: MpProposalModel.NONE
    stubCase = when (query.debug?.stubCase) {
        MpRequestProposalCreate.StubCase.SUCCESS -> MpStubCase.PROPOSAL_CREATE_SUCCESS
        else -> MpStubCase.NONE
    }
}

fun MpBeContext.setQuery(query: MpRequestProposalUpdate) = setQuery(query) {
    requestProposal = query.updateData?.toModel()?: MpProposalModel.NONE
    stubCase = when (query.debug?.stubCase) {
        MpRequestProposalUpdate.StubCase.SUCCESS -> MpStubCase.PROPOSAL_UPDATE_SUCCESS
        else -> MpStubCase.NONE
    }
}

fun MpBeContext.setQuery(query: MpRequestProposalDelete) = setQuery(query) {
    requestProposalId = query.proposalId?.let { MpProposalIdModel(it) }?: MpProposalIdModel.NONE
    stubCase = when (query.debug?.stubCase) {
        MpRequestProposalDelete.StubCase.SUCCESS -> MpStubCase.PROPOSAL_DELETE_SUCCESS
        else -> MpStubCase.NONE
    }
}

fun MpBeContext.setQuery(query: MpRequestProposalList) = setQuery(query) {
    proposalFilter = query.filterData?.let {
        MpProposalFilterModel(
            text = it.text?: "",
            sortBy = it.sortBy?.let { MpSortModel.valueOf(it.name) }?: MpSortModel.NONE,
            offset = it.offset?: Int.MIN_VALUE,
            count = it.count?: Int.MIN_VALUE,
        )
    }?: MpProposalFilterModel.NONE
    stubCase = when (query.debug?.stubCase) {
        MpRequestProposalList.StubCase.SUCCESS -> MpStubCase.PROPOSAL_LIST_SUCCESS
        else -> MpStubCase.NONE
    }
}

fun MpBeContext.setQuery(query: MpRequestProposalOffers) = setQuery(query) {
    requestProposalId = query.proposalId?.let { MpProposalIdModel(it) }?: MpProposalIdModel.NONE
    stubCase = when (query.debug?.stubCase) {
        MpRequestProposalOffers.StubCase.SUCCESS -> MpStubCase.PROPOSAL_OFFERS_SUCCESS
        else -> MpStubCase.NONE
    }
}

fun MpBeContext.respondProposalRead() = MpResponseProposalRead(
    proposal = responseProposal.takeIf { it != MpProposalModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun MpBeContext.respondProposalCreate() = MpResponseProposalCreate(
    proposal = responseProposal.takeIf { it != MpProposalModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun MpBeContext.respondProposalUpdate() = MpResponseProposalUpdate(
    proposal = responseProposal.takeIf { it != MpProposalModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun MpBeContext.respondProposalDelete() = MpResponseProposalDelete(
    proposal = responseProposal.takeIf { it != MpProposalModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun MpBeContext.respondProposalList() = MpResponseProposalList(
    proposals = responseProposals.takeIf { it.isNotEmpty() }?.filter { it != MpProposalModel.NONE }
        ?.map { it.toTransport() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun MpBeContext.respondProposalOffers() = MpResponseProposalOffers(
     proposalDemands = responseDemands.takeIf { it.isNotEmpty() }?.filter { it != MpDemandModel.NONE}
        ?.map { it.toTransport() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
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
