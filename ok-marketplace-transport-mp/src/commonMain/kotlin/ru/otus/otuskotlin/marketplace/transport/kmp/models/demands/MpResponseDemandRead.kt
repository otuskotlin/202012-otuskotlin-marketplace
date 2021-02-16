package ru.otus.otuskotlin.marketplace.transport.kmp.models.demands

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.*

@Serializable
@SerialName("MpResponseDemandRead")
data class MpResponseDemandRead(
    override val responseId: String? = null,
    override val onRequest: String? = null,
    override val endTime: String? = null,
    override val debug: Debug? = null,
    override val errors: List<ErrorDto>? = null,
    override val status: ResponseStatusDto? = null,
    val demand: MpDemandDto? = null,
    ): IMpResponse, MpMessage() {

    @Serializable
    data class Debug(
        override val mode: MpWorkModeDto?
    ) : IMpDebug
}
