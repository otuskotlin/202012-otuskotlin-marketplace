package ru.otus.otuskotlin.marketplace.backend.repository.sql.schema

import org.jetbrains.exposed.dao.id.UUIDTable

object DemandsTable: UUIDTable("demands") {

    val avatar = varchar("avatar", 256)
    val title = text("title")
    val description = text("description")
//    val tagIds  определяется в DemandsTagsTable
//    val techDets = reference("tech_dets", TechDetsTable)
}
