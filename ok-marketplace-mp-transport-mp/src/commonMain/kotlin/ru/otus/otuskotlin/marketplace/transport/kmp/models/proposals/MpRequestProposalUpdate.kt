package ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpDebug
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpRequest
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpWorkModeDto

@Serializable
@SerialName("MpRequestProposalUpdate")
data class MpRequestProposalUpdate(
    override val requestId: String? = null,
    override val debug: Debug? = null,
    override val onResponse: String? = null,
    override val startTime: String? = null,
    val updateData: MpProposalUpdateDto? = null,
): IMpRequest, MpMessage() {

    @Serializable
    data class Debug(
        override val mode: MpWorkModeDto? = null,
        val stubCase: StubCase? = null
    ) : IMpDebug

    @Serializable
    enum class StubCase {
        NONE,
        SUCCESS
    }
}
