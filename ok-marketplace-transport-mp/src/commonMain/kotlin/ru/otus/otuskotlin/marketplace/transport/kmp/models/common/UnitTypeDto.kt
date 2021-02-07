package ru.otus.otuskotlin.marketplace.transport.kmp.models.common

import kotlinx.serialization.Serializable

@Serializable
data class UnitTypeDto(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val synonyms: Set<String>? = null,
    val symbol: String? = null,
    val symbols: Set<String>? = null,
    val isBase: Boolean? = null,
)
