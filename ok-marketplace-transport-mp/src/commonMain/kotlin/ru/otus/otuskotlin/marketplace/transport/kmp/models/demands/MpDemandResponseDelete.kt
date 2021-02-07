package ru.otus.otuskotlin.marketplace.transport.kmp.models.demands

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.DebugDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ErrorDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpResponse
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto

@Serializable
@SerialName("MpDemandResponseDelete")
data class MpDemandResponseDelete(
    override val id: String? = null,
    override val debug: DebugDto? = null,
    override val onRequest: String? = null,
    override val endTime: String? = null,
    override val errors: List<ErrorDto>? = null,
    override val status: ResponseStatusDto? = null,
    val demand: DemandDto? = null,
    val deleted: Boolean? = null,
): IMpResponse
