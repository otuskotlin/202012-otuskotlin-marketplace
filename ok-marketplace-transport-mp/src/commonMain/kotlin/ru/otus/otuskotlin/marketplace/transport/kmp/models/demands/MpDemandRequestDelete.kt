package ru.otus.otuskotlin.marketplace.transport.kmp.models.demands

import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.DebugDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpRequest

data class MpDemandRequestDelete(
    override val id: String? = null,
    override val debug: DebugDto? = null,
    override val onResponse: String? = null,
    override val startTime: String? = null,
    val demandId: String? = null,
): IMpRequest
