package ru.otus.otuskotlin.marketplace.transport.kmp.models.demands

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpFilterItem
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpSortDto

@Serializable
data class MpDemandListFilterDto(
    override val text: String? = null,
    override val sortBy: MpSortDto? = null,
    override val offset: Int? = null,
    override val count: Int? = null,
    ):IMpFilterItem
