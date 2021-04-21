package ru.otus.otuskotlin.marketplace.backend.repository.inmemory.demands

import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpTechDetModel

data class DemandInMemoryDto(
    val id: String? = null,
    val avatar: String? = null,
    val title: String? = null,
    val description: String? = null,
    val tagIds: Set<String>? = null,
    val techDets: Set<MpTechDetModel>? = null,
) {
    fun toModel() = MpDemandModel(
        id = id?.let { MpDemandIdModel(it) }?: MpDemandIdModel.NONE,
        avatar = avatar?: "",
        title = title?: "",
        description = description?: "",
        tagIds = tagIds?.toMutableSet()?: mutableSetOf(),
        techDets = techDets?.toMutableSet()?: mutableSetOf(),
    )

    companion object {

        fun of(model: MpDemandModel) = of(model, model.id.id)

        fun of(model: MpDemandModel, id: String) = DemandInMemoryDto(
            id = id.takeIf { it.isNotBlank() },
            avatar = model.avatar.takeIf { it.isNotBlank() },
            title = model.title.takeIf { it.isNotBlank() },
            description = model.description.takeIf { it.isNotBlank() },
            tagIds = model.tagIds.takeIf { it.isNotEmpty() },
            techDets = model.techDets.takeIf { it.isNotEmpty() },
        )
    }
}
