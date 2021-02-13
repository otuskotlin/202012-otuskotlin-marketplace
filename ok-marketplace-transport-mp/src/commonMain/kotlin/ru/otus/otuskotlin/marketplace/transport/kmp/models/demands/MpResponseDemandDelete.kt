package ru.otus.otuskotlin.marketplace.transport.kmp.models.demands

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.*

@Serializable
@SerialName("MpResponseDemandDelete")
data class MpResponseDemandDelete(
    override val responseId: String? = null,
    override val debug: DebugDto? = null,
    override val onRequest: String? = null,
    override val endTime: String? = null,
    override val errors: List<ErrorDto>? = null,
    override val status: ResponseStatusDto? = null,
    val demand: MpDemandDto? = null,
    val deleted: Boolean? = null,
): IMpResponse, MpMessage()
