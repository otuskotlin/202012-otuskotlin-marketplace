package ru.otus.otuskotlin.marketplace.transport.kmp.models.demands

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.DebugDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpRequest

@Serializable
@SerialName("MpRequestDemandUpdate")
data class MpRequestDemandUpdate(
    override val id: String? = null,
    override val debug: DebugDto? = null,
    override val onResponse: String? = null,
    override val startTime: String? = null,
    val updateData: MpDemandUpdateDto? = null,
): IMpRequest
