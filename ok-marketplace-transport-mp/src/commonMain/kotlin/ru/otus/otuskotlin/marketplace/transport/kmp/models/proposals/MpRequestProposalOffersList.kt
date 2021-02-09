package ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.DebugDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpRequest

@Serializable
@SerialName("MpRequestProposalOffersList")
data class MpRequestProposalOffersList(
    override val id: String? = null,
    override val onResponse: String? = null,
    override val startTime: String? = null,
    override val debug: DebugDto? = null,
    val proposalId: String? = null,
): IMpRequest
