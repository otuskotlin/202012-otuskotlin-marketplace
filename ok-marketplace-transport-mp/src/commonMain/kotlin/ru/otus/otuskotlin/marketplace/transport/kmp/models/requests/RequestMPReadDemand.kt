package ru.otus.otuskotlin.marketplace.transport.kmp.models.requests

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.QueryMode
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.DemandIdDto

@Serializable
data class RequestMPReadDemand(
    override val requestId: String? = null,
    override val mode: QueryMode? = null,
    override val requestData: DemandIdDto? = null,
): IRequestMP<DemandIdDto>
