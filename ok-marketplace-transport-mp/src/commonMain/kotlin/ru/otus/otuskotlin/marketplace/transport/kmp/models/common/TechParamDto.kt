package ru.otus.otuskotlin.marketplace.transport.kmp.models.common

import kotlinx.serialization.Serializable

@Serializable
data class TechParamDto(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val priority: Double? = null,
    val units: Set<UnitTypeDto>? = null,
)
