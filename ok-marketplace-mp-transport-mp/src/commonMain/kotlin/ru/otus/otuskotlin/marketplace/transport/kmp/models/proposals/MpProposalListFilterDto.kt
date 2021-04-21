package ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpFilterItem
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpSortDto

@Serializable
data class MpProposalListFilterDto(
    override val text: String? = null,
    override val sortBy: MpSortDto? = null,
    override val offset: Int? = null,
    override val count: Int? = null,
    override val includeDescription: Boolean? = null,
): IMpFilterItem
