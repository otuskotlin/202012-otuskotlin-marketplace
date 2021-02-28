package ru.otus.otuskotlin.marketplace.mappers.openapi

import ru.otus.otuskotlin.marketplace.common.backend.models.MpTechDetIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpTechDetModel
import ru.otus.otuskotlin.marketplace.transport.openapi.demand.models.MpTechDet

fun MpTechDet.toTechDetModel() = MpTechDetModel(
    id = this.id?.let { MpTechDetIdModel(it) } ?: MpTechDetIdModel.NONE,
    value = this.value ?: "",
    comparableValue = this.comparableValue ?: Double.MIN_VALUE,
)
