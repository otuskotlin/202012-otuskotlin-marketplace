package ru.otus.otuskotlin.marketplace.transport.kmp.models.demands

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpProposalDto

@Serializable
@SerialName("MpResponseDemandOffers")
data class MpResponseDemandOffers(
    override val responseId: String? = null,
    override val onRequest: String? = null,
    override val endTime: String? = null,
    override val errors: List<ErrorDto>? = null,
    override val status: ResponseStatusDto? = null,
    override val debug: Debug? = null,
    val demandProposals: List<MpProposalDto>? = null,
): IMpResponse, MpMessage() {

    @Serializable
    data class Debug(
        override val mode: MpWorkModeDto?
    ) : IMpDebug
}
