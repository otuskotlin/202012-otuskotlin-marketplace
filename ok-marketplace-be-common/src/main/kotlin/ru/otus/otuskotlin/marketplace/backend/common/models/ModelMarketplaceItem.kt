package ru.otus.otuskotlin.marketplace.backend.common.models

data class ModelMarketplaceItem(
    val id: ModelItemId = ModelItemId.NONE,
    val avatar: String = "",
    val title: String = "",
    val description: String = "",
    val linkView: String = "",
    val linkEdit: String = "",
    val linkDelete: String = "",
    val tags: MutableSet<ModelTag> = mutableSetOf(),
    //val techDets: MutableSet<ModelTechDet> = mutableSetOf(),
)
