package ru.otus.otuskotlin.marketplace.transport.kmp.models.demands

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.DebugDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpRequest
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage

@Serializable
@SerialName("MpRequestDemandCreate")
data class MpRequestDemandCreate(
    override val requestId: String? = null,
    override val debug: DebugDto? = null,
    override val onResponse: String? = null,
    override val startTime: String? = null,
    val createData: MpDemandCreateDto? = null,
): MpMessage(), IMpRequest
