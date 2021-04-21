package ru.otus.otuskotlin.marketplace.backend.repository.sql.schema

import org.jetbrains.exposed.dao.id.UUIDTable

object DemandsTagsTable: UUIDTable("demands-tags") {
    val tagId = varchar("tag_id", 64).index()
    var demand = reference("demand_id", DemandsTable)
}
