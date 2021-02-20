package ru.otus.otuskotlin.marketplace.transport.kmp.models.demands

import kotlinx.serialization.Serializable
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpFilterItem

@Serializable
data class MpDemandListFilterDto(
    override val text: String? = null,
    ):IMpFilterItem
