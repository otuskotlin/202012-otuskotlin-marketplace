package ru.otus.otuskotlin.marketplace.backend.repository.sql.schema

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import java.util.*

class DemandDto(id: EntityID<UUID>): UUIDEntity(id) {
    var avatar by DemandsTable.avatar
    var title by DemandsTable.title
    var description by DemandsTable.description
    val tagIds by DemandTagDto referrersOn DemandsTagsTable.demand

    fun toModel() = MpDemandModel(
        id = MpDemandIdModel(id.value.toString()) ?: MpDemandIdModel.NONE,
        avatar = avatar?: "",
        title = title?: "",
        description = description?: "",
        tagIds = tagIds.limit(10).map { it.tagId }.toMutableSet(),
    )

    fun of(model: MpDemandModel) {
        avatar = model.avatar
        title = model.title
        description = model.description
    }

    companion object: UUIDEntityClass<DemandDto>(DemandsTable) {
    }
}

fun x() {
    DemandDto.new {
    }
}
