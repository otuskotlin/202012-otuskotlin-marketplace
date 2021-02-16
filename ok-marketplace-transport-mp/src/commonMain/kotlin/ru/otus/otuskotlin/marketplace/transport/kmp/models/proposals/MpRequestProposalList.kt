package ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpDebug
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpRequest
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpWorkModeDto

@Serializable
@SerialName("MpRequestProposalList")
data class MpRequestProposalList(
    override val requestId: String? = null,
    override val onResponse: String? = null,
    override val startTime: String? = null,
    override val debug: Debug? = null,
    val filterData: MpProposalListFilterDto? = null,
): IMpRequest, MpMessage() {

    @Serializable
    data class Debug(
        override val mode: MpWorkModeDto?
    ) : IMpDebug
}
