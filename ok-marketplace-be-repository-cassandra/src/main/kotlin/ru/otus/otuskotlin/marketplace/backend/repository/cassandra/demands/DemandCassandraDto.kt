package ru.otus.otuskotlin.marketplace.backend.repository.cassandra.demands

import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import ru.otus.otuskotlin.marketplace.backend.repository.cassandra.common.dto.TechDetCassandraDto
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel

@Entity
data class DemandCassandraDto(
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
    fun toModel() = MpDemandModel(
        id = id?.let { MpDemandIdModel(it) }?: MpDemandModel.NONE.id,
        avatar = avatar?: MpDemandModel.NONE.avatar,
        title = title?: MpDemandModel.NONE.title,
        description = description?: MpDemandModel.NONE.description,
        tagIds = tagIds?.toMutableSet()?: MpDemandModel.NONE.tagIds,
        techDets = techDets?.map { it.toModel() }?.toMutableSet()?: MpDemandModel.NONE.techDets,
    )

    companion object {
        const val DEMANDS_TABLE_NAME = "demands"
        const val ID = "id"
        const val AVATAR = "avatar"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val TAG_ID_LIST = "tag_id_list"
        const val TECH_DETS = "tech_dets"
        const val LOCK_VERSION = "lock_version"

        fun of(model: MpDemandModel) = of(model, model.id.id)

        fun of(model: MpDemandModel, id: String) = DemandCassandraDto(
            id = id.takeIf { it != MpDemandModel.NONE.id.id },
            avatar = model.avatar.takeIf { it != MpDemandModel.NONE.avatar },
            title = model.title.takeIf { it != MpDemandModel.NONE.title },
            description = model.description.takeIf { it != MpDemandModel.NONE.description },
            tagIds = model.tagIds.takeIf { it != MpDemandModel.NONE.tagIds },
            techDets = model.techDets.takeIf { it != MpDemandModel.NONE.techDets }?.map { TechDetCassandraDto.of(it) }?.toSet(),
        )
    }
}
