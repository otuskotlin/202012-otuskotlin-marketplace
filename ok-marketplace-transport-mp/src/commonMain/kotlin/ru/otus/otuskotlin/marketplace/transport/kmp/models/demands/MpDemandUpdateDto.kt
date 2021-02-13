package ru.otus.otuskotlin.marketplace.transport.kmp.models.demands

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpItemUpdateDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechDetsDto

@Serializable
data class MpDemandUpdateDto(
    override val avatar: String? = null,
    override val title: String? = null,
    override val description: String? = null,
    override val tagIds: Set<String>? = null,
    override val techDets: Set<TechDetsDto>? = null,
    override val id: String? = null,
) : IMpItemUpdateDto
