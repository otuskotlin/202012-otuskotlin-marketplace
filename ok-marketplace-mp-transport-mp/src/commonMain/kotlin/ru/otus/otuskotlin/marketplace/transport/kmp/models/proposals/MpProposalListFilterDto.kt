package ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpFilterItem

@Serializable
data class MpProposalListFilterDto(
    override val text: String? = null,
): IMpFilterItem
