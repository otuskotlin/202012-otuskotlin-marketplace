package ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.DebugDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ErrorDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpResponse
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto

@Serializable
@SerialName("MpResponseProposalUpdate")
data class MpResponseProposalUpdate(
    override val id: String? = null,
    override val onRequest: String? = null,
    override val endTime: String? = null,
    override val debug: DebugDto? = null,
    override val errors: List<ErrorDto>? = null,
    override val status: ResponseStatusDto? = null,
    val proposal: MpProposalDto? = null,
): IMpResponse
