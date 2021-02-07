package ru.otus.otuskotlin.marketplace.transport.kmp.models.demands

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TagDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechDetsDto

@Serializable
data class DemandDto(
    val id: String? = null,
    val avatar: String? = null,
    val title: String? = null,
    val descriptor: String? = null,
    val tags: Set<TagDto>? = null,
    val techDets: Set<TechDetsDto>? = null,
)
