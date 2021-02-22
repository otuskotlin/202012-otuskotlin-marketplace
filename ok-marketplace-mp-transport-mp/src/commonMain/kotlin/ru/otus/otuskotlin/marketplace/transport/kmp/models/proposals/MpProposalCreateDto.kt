package ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpItemCreateDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechDetsDto

@Serializable
data class MpProposalCreateDto(
    override val avatar: String? = null,
    override val title: String? = null,
    override val description: String? = null,
    override val tagIds: Set<String>? = null,
    override val techDets: Set<TechDetsDto>? = null,
) : IMpItemCreateDto
