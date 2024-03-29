package ru.otus.otuskotlin.marketplace.backend.mappers.kmp

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpWorkModeDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*
import java.time.Instant

fun MpBeContext.setQuery(query: MpRequestDemandRead) = setQuery(query) {
    requestDemandId = query.demandId?.let { MpDemandIdModel(it) }?: MpDemandIdModel.NONE
    stubCase = when (query.debug?.stubCase) {
        MpRequestDemandRead.StubCase.SUCCESS -> MpStubCase.DEMAND_READ_SUCCESS
        else -> MpStubCase.NONE
    }
    workMode = query.debug?.mode.toModel()
}

fun MpBeContext.setQuery(query: MpRequestDemandCreate) = setQuery(query) {
    requestDemand = query.createData?.toModel()?: MpDemandModel.NONE
    stubCase = when (query.debug?.stubCase) {
        MpRequestDemandCreate.StubCase.SUCCESS -> MpStubCase.DEMAND_CREATE_SUCCESS
        else -> MpStubCase.NONE
    }
    workMode = query.debug?.mode.toModel()
}

fun MpBeContext.setQuery(query: MpRequestDemandUpdate) = setQuery(query) {
    requestDemand = query.updateData?.toModel()?: MpDemandModel.NONE
    stubCase = when (query.debug?.stubCase) {
        MpRequestDemandUpdate.StubCase.SUCCESS -> MpStubCase.DEMAND_UPDATE_SUCCESS
        else -> MpStubCase.NONE
    }
    workMode = query.debug?.mode.toModel()
}

fun MpBeContext.setQuery(query: MpRequestDemandDelete) = setQuery(query) {
    requestDemandId = query.demandId?.let { MpDemandIdModel(it) }?: MpDemandIdModel.NONE
    stubCase = when (query.debug?.stubCase) {
        MpRequestDemandDelete.StubCase.SUCCESS -> MpStubCase.DEMAND_DELETE_SUCCESS
        else -> MpStubCase.NONE
    }
    workMode = query.debug?.mode.toModel()
}

fun MpBeContext.setQuery(query: MpRequestDemandList) = setQuery(query) {
    demandFilter = query.filterData?.let {
        MpDemandFilterModel(
            text = it.text?: "",
            includeDescription = it.includeDescription?: false,
            sortBy = it.sortBy?.let { MpSortModel.valueOf(it.name) }?: MpSortModel.NONE,
            offset = it.offset?: Int.MIN_VALUE,
            count = it.count?: Int.MIN_VALUE,
        )
    }?: MpDemandFilterModel.NONE
    stubCase = when (query.debug?.stubCase) {
        MpRequestDemandList.StubCase.SUCCESS -> MpStubCase.DEMAND_LIST_SUCCESS
        else -> MpStubCase.NONE
    }
    workMode = query.debug?.mode.toModel()
}

fun MpBeContext.setQuery(query: MpRequestDemandOffers) = setQuery(query) {
    requestDemandId = query.demandId?.let { MpDemandIdModel(it) }?: MpDemandIdModel.NONE
    stubCase = when (query.debug?.stubCase) {
        MpRequestDemandOffers.StubCase.SUCCESS -> MpStubCase.DEMAND_OFFERS_SUCCESS
        else -> MpStubCase.NONE
    }
    workMode = query.debug?.mode.toModel()
}

fun MpBeContext.respondDemandCreate() = MpResponseDemandCreate(
    demand = responseDemand.takeIf { it != MpDemandModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString(),
    debug = MpResponseDemandCreate.Debug(
        mode = workMode.takeIf { it != MpWorkMode.DEFAULT }?.toTransport()
    )
)

fun MpBeContext.respondDemandRead() = MpResponseDemandRead(
    demand = responseDemand.takeIf { it != MpDemandModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString(),
    debug = MpResponseDemandRead.Debug(
        mode = workMode.takeIf { it != MpWorkMode.DEFAULT }?.toTransport()
    )
)

fun MpBeContext.respondDemandUpdate() = MpResponseDemandUpdate(
    demand = responseDemand.takeIf { it != MpDemandModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString(),
    debug = MpResponseDemandUpdate.Debug(
        mode = workMode.takeIf { it != MpWorkMode.DEFAULT }?.toTransport()
    )
)

fun MpBeContext.respondDemandDelete() = MpResponseDemandDelete(
    demand = responseDemand.takeIf { it != MpDemandModel.NONE }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString(),
    debug = MpResponseDemandDelete.Debug(
        mode = workMode.takeIf { it != MpWorkMode.DEFAULT }?.toTransport()
    )
)

fun MpBeContext.respondDemandList() = MpResponseDemandList(
    demands = responseDemands.takeIf { it.isNotEmpty() }?.filter { it != MpDemandModel.NONE }
        ?.map { it.toTransport() },
    pageCount = pageCount.takeIf {it != Int.MIN_VALUE},
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString(),
    debug = MpResponseDemandList.Debug(
        mode = workMode.takeIf { it != MpWorkMode.DEFAULT }?.toTransport()
    )
)

fun MpBeContext.respondDemandOffers() = MpResponseDemandOffers(
    demandProposals = responseProposals.takeIf { it.isNotEmpty() }?.filter { it != MpProposalModel.NONE}
        ?.map { it.toTransport() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    status = status.toTransport(),
    responseId = responseId,
    onRequest = onRequest,
    endTime = Instant.now().toString(),
    debug = MpResponseDemandOffers.Debug(
        mode = workMode.takeIf { it != MpWorkMode.DEFAULT }?.toTransport()
    )
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

