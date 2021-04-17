package ru.otus.otuskotlin.marketplace.backend.repository.cassandra.proposals

import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import ru.otus.otuskotlin.marketplace.backend.repository.cassandra.common.TechDetCassandraDto
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel

@Entity
data class ProposalCassandraDto(
    @PartitionKey
    @CqlName(ID)
    val id: String? = null,
    @CqlName(AVATAR)
    val avatar: String? = null,
    @CqlName(TITLE)
    val title: String? = null,
    @CqlName(DESCRIPTION)
    val description: String? = null,
    @CqlName(TAG_ID_LIST)
    val tagIds: Set<String>? = null,
    @CqlName(TECH_DETS)
    val techDets: Set<TechDetCassandraDto>? = null,
    @CqlName(LOCK_VERSION)
    val lockVersion: String? = null,
) {
    fun toModel() = MpProposalModel(
        id = id?.let { MpProposalIdModel(it) }?: MpProposalModel.NONE.id,
        avatar = avatar?: MpProposalModel.NONE.avatar,
        title = title?: MpProposalModel.NONE.title,
        description = description?: MpProposalModel.NONE.description,
        tagIds = tagIds?.toMutableSet()?: MpProposalModel.NONE.tagIds,
        techDets = techDets?.map { it.toModel() }?.toMutableSet()?: MpProposalModel.NONE.techDets,
    )

    companion object {
        const val ID = "id"
        const val AVATAR = "avatar"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val TAG_ID_LIST = "tag_id_list"
        const val TECH_DETS = "tech_dets"
        const val LOCK_VERSION = "lock_version"

        fun of(model: MpProposalModel) = of(model, model.id.id)

        fun of(model: MpProposalModel, id: String) = ProposalCassandraDto(
            id = id.takeIf { it != MpProposalModel.NONE.id.id },
            avatar = model.avatar.takeIf { it != MpProposalModel.NONE.avatar },
            title = model.title.takeIf { it != MpProposalModel.NONE.title },
            description = model.description.takeIf { it != MpProposalModel.NONE.description },
            tagIds = model.tagIds.takeIf { it != MpProposalModel.NONE.tagIds },
            techDets = model.techDets.takeIf { it != MpProposalModel.NONE.techDets }?.map { TechDetCassandraDto.of(it) }?.toSet(),
        )
    }
}
