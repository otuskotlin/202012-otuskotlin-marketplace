package ru.otus.otuskotlin.marketplace.transport.kmp.models.responses

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.DebugDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.DemandDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ErrorDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto

@Serializable
data class MpDemandResponse<T>(
    override val type: String? = this::class.simpleName,
    override val id: String? = null,
    override val onRequest: String?,
    override val endTime: String?,
    override val debug: DebugDto? = null,
    override val errors: List<ErrorDto>? = null,
    override val status: ResponseStatusDto? = null,
    val responseDemand: DemandDto? = null,
    ): IResponseMP
