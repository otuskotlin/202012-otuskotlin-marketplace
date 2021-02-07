package ru.otus.otuskotlin.marketplace.transport.kmp.models.requests

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.DebugDto

@Serializable
data class MpDemandRequestRead(
    override val type: String? = this::class.simpleName,
    override val id: String? = null,
    override val onResponse: String?,
    override val startTime: String?,
    override val debug: DebugDto? = null,
    val demandId: String? = null,
): IRequestMP
