package ru.otus.otuskotlin.marketplace.backend.repository.sql.schema

import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import java.util.*

class DemandDto(id: EntityID<UUID>): AdDto<MpDemandModel, DemandDto>(id, DemandsTable, DemandsTagsTable, Companion) {

    fun toModel() = MpDemandModel(
        id = MpDemandIdModel(id.value.toString()),
        avatar = avatar?: "",
        title = title?: "",
        description = description?: "",
//        tagIds = tagIds.limit(100).map { it.tagId }.toMutableSet(),
    )

    companion object: UUIDEntityClass<DemandDto>(DemandsTable) {
    }
}
