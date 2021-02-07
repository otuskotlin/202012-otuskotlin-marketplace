package ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TagDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechDetsDto

@Serializable
data class ProposalDto(
    val id: String? = null,
    val avatar: String? = null,
    val title: String? = null,
    val description: String? = null,
    val tags: Set<TagDto>? = null,
    val techDets: Set<TechDetsDto>? = null,
)
