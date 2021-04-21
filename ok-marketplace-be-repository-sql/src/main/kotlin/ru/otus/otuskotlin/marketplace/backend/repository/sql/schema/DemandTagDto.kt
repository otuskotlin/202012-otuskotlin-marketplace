package ru.otus.otuskotlin.marketplace.backend.repository.sql.schema

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class DemandTagDto(id: EntityID<UUID>): UUIDEntity(id) {
    val tagId by DemandsTagsTable.tagId

    companion object: UUIDEntityClass<DemandTagDto>(DemandsTagsTable) {
    }
}
