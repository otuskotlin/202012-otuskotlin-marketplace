package ru.otus.otuskotlin.marketplace.transport.kmp.models.common

import kotlinx.serialization.Serializable
@Serializable
data class DemandDto(
    val id: String? = null,
    val avatar: String? = null,
    val title: String? = null,
    val descriptor: String? = null,
    val tags: Set<TagDto>? = null,
    val techDets: Set<TechDetsDto>? = null,
)
