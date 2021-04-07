package ru.otus.otuskotlin.marketplace.backend.repository.inmemory.proposals

import ru.otus.otuskotlin.marketplace.common.backend.models.*

data class ProposalInMemoryDto(
    val id: String? = null,
    val avatar: String? = null,
    val title: String? = null,
    val description: String? = null,
    val tagIds: Set<String>? = null,
    val techDets: Set<MpTechDetModel>? = null,
) {

    fun toModel() = MpProposalModel(
        id = id?.let { MpProposalIdModel(it) }?: MpProposalIdModel.NONE,
        avatar = avatar?: "",
        title = title?: "",
        description = description?: "",
        tagIds = tagIds?.toMutableSet()?: mutableSetOf(),
        techDets = techDets?.toMutableSet()?: mutableSetOf(),
    )

    companion object {
        fun of(model: MpProposalModel) = of(model, model.id.id)

        fun of(model: MpProposalModel, id: String) = ProposalInMemoryDto(
            id = id.takeIf { it.isNotBlank() },
            avatar = model.avatar.takeIf { it.isNotBlank() },
            title = model.title.takeIf { it.isNotBlank() },
            description = model.description.takeIf { it.isNotBlank() },
            tagIds = model.tagIds.takeIf { it.isNotEmpty() },
            techDets = model.techDets.takeIf { it.isNotEmpty() },
        )
    }

}
