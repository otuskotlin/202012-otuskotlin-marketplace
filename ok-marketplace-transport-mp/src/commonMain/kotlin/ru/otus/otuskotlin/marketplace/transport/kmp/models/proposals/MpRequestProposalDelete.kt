package ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.DebugDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpRequest

@Serializable
@SerialName("MpRequestProposalDelete")
data class MpRequestProposalDelete(
    override val requestId: String? = null,
    override val debug: DebugDto? = null,
    override val onResponse: String? = null,
    override val startTime: String? = null,
    val proposalId: String? = null,
): IMpRequest
