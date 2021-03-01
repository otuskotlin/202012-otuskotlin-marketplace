package ru.otus.otuskotlin.marketplace.mappers.openapi

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpRequest
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandCreate
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandRead

private fun MpBeContext.setQuery(request: MpRequestDemandRead) {
    this.requestDemandId = request.demandId?.let { MpDemandIdModel(it) }?: MpDemandIdModel.NONE
}

fun MpBeContext.setQuery(request: IMpRequest) =
    when(request){
        is MpRequestDemandRead -> setQuery(request)
        is MpRequestDemandCreate -> setQuery(request)
        else -> null
    }

private fun MpBeContext.setQuery(request: MpRequestDemandCreate) {
    request.createData?.let { data ->
        this.requestDemand = MpDemandModel(
            avatar = data.avatar ?: "",
            title = data.title ?: "",
            description = data.description ?: "",
            tagIds = data.tagIds?.toMutableSet() ?: mutableSetOf(),
            techDets = data.techDets?.map { it.toTechDetModel() }?.toMutableSet() ?: mutableSetOf()
        )
    }
}
