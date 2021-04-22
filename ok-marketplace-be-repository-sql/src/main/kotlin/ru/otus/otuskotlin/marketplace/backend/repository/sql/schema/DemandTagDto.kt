package ru.otus.otuskotlin.marketplace.backend.repository.sql.schema

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class DemandTagDto(id: EntityID<UUID>): UUIDEntity(id) {
    var tagId by DemandsTagsTable.tagId
    var demand by DemandDto referencedOn DemandsTagsTable.demand

    companion object: UUIDEntityClass<DemandTagDto>(DemandsTagsTable) {
    }
}
