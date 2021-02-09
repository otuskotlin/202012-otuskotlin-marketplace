package ru.otus.otuskotlin.marketplace.transport.kmp.models.demands

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.DebugDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpRequest

@Serializable
@SerialName("MpRequestDemandRead")
data class MpRequestDemandRead(
    override val id: String? = null,
    override val onResponse: String? = null,
    override val startTime: String? = null,
    override val debug: DebugDto? = null,
    val demandId: String? = null,
): IMpRequest
