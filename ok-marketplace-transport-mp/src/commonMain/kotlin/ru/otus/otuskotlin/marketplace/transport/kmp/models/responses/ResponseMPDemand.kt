package ru.otus.otuskotlin.marketplace.transport.kmp.models.responses

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.QueryMode
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.DemandDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ErrorDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto

@Serializable
data class ResponseMPDemand<T>(
    override val requestId: String? = null,
    override val mode: QueryMode? = null,
    override val requestData: T? = null,
    override val responseData: DemandDto? = null,
    override val errors: List<ErrorDto>? = null,
    override val status: ResponseStatusDto? = null,

    ): IResponseMP<T, DemandDto>
