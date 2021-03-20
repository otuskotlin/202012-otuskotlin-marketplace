package ru.otus.otuskotlin.marketplace.backend.mappers

import ru.otus.otuskotlin.marketplace.backend.mappers.exceptions.WrongMpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus.*
import ru.otus.otuskotlin.marketplace.common.backend.models.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ErrorDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*

fun MpBeContext.setQuery(query: MpRequestDemandRead) = apply {
    requestDemandId = query.demandId?.let { MpDemandIdModel(it) }?: MpDemandIdModel.NONE
    stubCase = when (query.stubCase) {
        MpRequestDemandRead.StubCase.SUCCESS -> MpStubCase.DEMAND_READ_SUCCESS
        else -> MpStubCase.NONE
    }
}

fun MpBeContext.setException(e: Throwable) = apply {
    frameworkErrors.add(e)
}

fun MpBeContext.setQuery(query: MpRequestDemandCreate) = apply {
    requestDemand = query.createData?.toModel()?: MpDemandModel.NONE
}

fun MpBeContext.setQuery(query: MpRequestDemandUpdate) = apply {
    requestDemand = query.updateData?.toModel()?: MpDemandModel.NONE
}

fun MpBeContext.setQuery(query: MpRequestDemandDelete) = apply {
    requestDemandId = query.demandId?.let { MpDemandIdModel(it) }?: MpDemandIdModel.NONE
}

fun MpBeContext.setQuery(query: MpRequestDemandList) = apply {
    demandFilter = query.filterData?.let {
        MpDemandFilterModel(
            text = it.text?: ""
        )
    }?: MpDemandFilterModel.NONE
}

fun MpBeContext.setQuery(query: MpRequestDemandOffersList) = apply {
    requestDemandId = query.demandId?.let { MpDemandIdModel(it) }?: MpDemandIdModel.NONE
}

fun MpBeContext.respondDemandGet() = MpResponseDemandRead(
    demand = responseDemand.takeIf { it != MpDemandModel.NONE }?.toTransport(),
    errors = errors.map { it.toTransport() },
    status = status.toTransport()
)

private fun IMpError.toTransport() = ErrorDto(
    message = message
)

fun MpBeContextStatus.toTransport() = when(this) {
    NONE -> throw WrongMpBeContextStatus(this)
    RUNNING -> throw WrongMpBeContextStatus(this)
    FINISHING -> throw WrongMpBeContextStatus(this)
    FAILING -> throw WrongMpBeContextStatus(this)
    SUCCESS -> ResponseStatusDto.SUCCESS
    ERROR -> ResponseStatusDto.BAD_REQUEST
}

fun MpBeContext.respondDemandCreate() = MpResponseDemandCreate(
    demand = responseDemand.takeIf { it != MpDemandModel.NONE }?.toTransport()
)

fun MpBeContext.respondDemandUpdate() = MpResponseDemandUpdate(
    demand = responseDemand.takeIf { it != MpDemandModel.NONE }?.toTransport()
)

fun MpBeContext.respondDemandDelete() = MpResponseDemandDelete(
    demand = responseDemand.takeIf { it != MpDemandModel.NONE }?.toTransport()
)

fun MpBeContext.respondDemandList() = MpResponseDemandList(
    demands = responseDemands.takeIf { it.isNotEmpty() }?.filter { it != MpDemandModel.NONE }
        ?.map { it.toTransport() }
)

fun MpBeContext.respondDemandOffers() = MpResponseDemandOffersList(
    demandProposals = responseProposals.takeIf { it.isNotEmpty() }?.filter { it != MpProposalModel.NONE}
        ?.map { it.toTransport() }
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

