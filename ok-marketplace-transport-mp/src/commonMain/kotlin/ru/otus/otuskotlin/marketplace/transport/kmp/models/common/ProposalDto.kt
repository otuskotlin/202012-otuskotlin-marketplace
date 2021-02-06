package ru.otus.otuskotlin.marketplace.transport.kmp.models.common

import kotlinx.serialization.Serializable

@Serializable
data class ProposalDto(
    val id: ProposalIdDto? = null,
    val avatar: String? = null,
    val title: String? = null,
    val descriptor: String? = null,
    val linkView: String? = null,
    val linkEdit: String? = null,
    val linkDelete: String? = null,
    val tags: Set<TagDto>? = null,
    val techDets: Set<TechDetsDto>? = null,
)
