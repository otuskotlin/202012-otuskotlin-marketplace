package ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.DebugDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpRequest

@Serializable
@SerialName("MpRequestProposalRead")
data class MpRequestProposalRead(
    override val id: String? = null,
    override val onResponse: String?,
    override val startTime: String?,
    override val debug: DebugDto? = null,
    val requestData: String? = null,
    ): IMpRequest
