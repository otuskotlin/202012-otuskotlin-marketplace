package ru.otus.otuskotlin.marketplace.transport.kmp.models.responses

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.QueryMode
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.DemandDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ErrorDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.requests.RequestMPReadDemand

@Serializable
data class ResponseMPDemand(
    override val requestId: String? = null,
    override val mode: QueryMode? = null,
    override val requestData: RequestMPReadDemand? = null,
    override val responseData: DemandDto? = null,
    override val errors: List<ErrorDto>? = null,
    override val status: ResponseStatusDto? = null,

    ): IResponseMP<RequestMPReadDemand, DemandDto>
