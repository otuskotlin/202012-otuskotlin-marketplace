package ru.otus.otuskotlin.marketplace.transport.kmp.models.requests

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ProposalIdDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.QueryMode

@Serializable
data class RequestMPReadProposal(
    override val requestId: String? = null,
    override val mode: QueryMode? = null,
    override val requestData: ProposalIdDto? = null,

    ): IRequestMP<ProposalIdDto>
