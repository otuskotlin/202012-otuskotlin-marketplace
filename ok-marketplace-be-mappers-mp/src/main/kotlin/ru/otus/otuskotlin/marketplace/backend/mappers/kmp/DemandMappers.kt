package ru.otus.otuskotlin.marketplace.backend.mappers.kmp

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*
import java.time.Instant

fun MpBeContext.setQuery(query: MpRequestDemandRead) = setQuery(query) {
    requestDemandId = query.demandId?.let { MpDemandIdModel(it) }?: MpDemandIdModel.NONE
    stubCase = when (query.debug?.stubCase) {
        MpRequestDemandRead.StubCase.SUCCESS -> MpStubCase.DEMAND_READ_SUCCESS
        else -> MpStubCase.NONE
    }
}

fun MpBeContext.setQuery(query: MpRequestDemandCreate) = setQuery(query) {
    requestDemand = query.createData?.toModel()?: MpDemandModel.NONE
    stubCase = when (query.debug?.stubCase) {
        MpRequestDemandCreate.StubCase.SUCCESS -> MpStubCase.DEMAND_CREATE_SUCCESS
        else -> MpStubCase.NONE
    }
}

fun MpBeContext.setQuery(query: MpRequestDemandUpdate) = setQuery(query) {
    requestDemand = query.updateData?.toModel()?: MpDemandModel.NONE
    stubCase = when (query.debug?.stubCase) {
        MpRequestDemandUpdate.StubCase.SUCCESS -> MpStubCase.DEMAND_UPDATE_SUCCESS
        else -> MpStubCase.NONE
    }
}

fun MpBeContext.setQuery(query: MpRequestDemandDelete) = setQuery(query) {
    requestDemandId = query.demandId?.let { MpDemandIdModel(it) }?: MpDemandIdModel.NONE
    stubCase = when (query.debug?.stubCase) {
        MpRequestDemandDelete.StubCase.SUCCESS -> MpStubCase.DEMAND_DELETE_SUCCESS
        else -> MpStubCase.NONE
    }
}

fun MpBeContext.setQuery(query: MpRequestDemandList) = setQuery(query) {
    demandFilter = query.filterData?.let {
        MpDemandFilterModel(
            text = it.text?: "",
            sortBy = it.sortBy?.let { MpSortModel.valueOf(it.name) }?: MpSortModel.NONE,
            offset = it.offset?: Int.MIN_VALUE,
            count = it.count?: Int.MIN_VALUE,
        )
    }?: MpDemandFilterModel.NONE
    stubCase = when (query.debug?.stubCase) {
        MpRequestDemandList.StubCase.SUCCESS -> MpStubCase.DEMAND_LIST_SUCCESS
        else -> MpStubCase.NONE
    }
}

fun MpBeContext.setQuery(query: MpRequestDemandOffers) = setQuery(query) {
    requestDemandId = query.demandId?.let { MpDemandIdModel(it) }?: MpDemandIdModel.NONE
    stubCase = when (query.debug?.stubCase) {
        MpRequestDemandOffers.StubCase.SUCCESS -> MpStubCase.DEMAND_OFFERS_SUCCESS
        else -> MpStubCase.NONE
    }
}

fun MpBeContext.respondDemandCreate() = MpResponseDemandCreate(
    demand = responseDemand.takeIf { it != MpDemandModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun MpBeContext.respondDemandRead() = MpResponseDemandRead(
    demand = responseDemand.takeIf { it != MpDemandModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun MpBeContext.respondDemandUpdate() = MpResponseDemandUpdate(
    demand = responseDemand.takeIf { it != MpDemandModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun MpBeContext.respondDemandDelete() = MpResponseDemandDelete(
    demand = responseDemand.takeIf { it != MpDemandModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun MpBeContext.respondDemandList() = MpResponseDemandList(
    demands = responseDemands.takeIf { it.isNotEmpty() }?.filter { it != MpDemandModel.NONE }
        ?.map { it.toTransport() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

fun MpBeContext.respondDemandOffers() = MpResponseDemandOffers(
    demandProposals = responseProposals.takeIf { it.isNotEmpty() }?.filter { it != MpProposalModel.NONE}
        ?.map { it.toTransport() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString()
)

private fun MpDemandUpdateDto.toModel() = MpDemandModel(
    id = id?.let { MpDemandIdModel(it) }?: MpDemandIdModel.NONE,
    avatar = avatar?: "",
    title = title?: "",
    description = description?: "",
    tagIds = tagIds?.toMutableSet()?: mutableSetOf(),
    techDets = techDets?.map { it.toModel() }?.toMutableSet()?: mutableSetOf()
)

private fun MpDemandCreateDto.toModel() = MpDemandModel(
    avatar = avatar?: "",
    title = title?: "",
    description = description?: "",
    tagIds = tagIds?.toMutableSet()?: mutableSetOf(),
    techDets = techDets?.map { it.toModel() }?.toMutableSet()?: mutableSetOf()
)

