package ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.*

@Serializable
@SerialName("MpResponseProposalDelete")
data class MpResponseProposalDelete(
    override val responseId: String? = null,
    override val debug: DebugDto? = null,
    override val onRequest: String? = null,
    override val endTime: String? = null,
    override val errors: List<ErrorDto>? = null,
    override val status: ResponseStatusDto? = null,
    val proposal: MpProposalDto? = null,
    val deleted: Boolean? = null,
): IMpResponse, MpMessage()
