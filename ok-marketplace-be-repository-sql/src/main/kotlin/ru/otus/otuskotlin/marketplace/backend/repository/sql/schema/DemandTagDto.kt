package ru.otus.otuskotlin.marketplace.backend.repository.sql.schema

import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class DemandTagDto(id: EntityID<UUID>) : AdTagDto<DemandDto>(id, DemandsTagsTable, DemandDto) {
    var demand
        get() = ad
        set(value) {
            ad = value
        }

    companion object : UUIDEntityClass<DemandTagDto>(DemandsTagsTable) {
    }
}
