package ru.otus.otuskotlin.marketplace.transport.kmp.models.common

import kotlinx.serialization.Serializable

@Serializable
data class TechDetsDto (
    val id: String? = null,
    val param: TechParamDto? = null,
    val value: String? = null,
    val unit: UnitTypeDto? = null,
    val comparableValue: Double? = null,
)
