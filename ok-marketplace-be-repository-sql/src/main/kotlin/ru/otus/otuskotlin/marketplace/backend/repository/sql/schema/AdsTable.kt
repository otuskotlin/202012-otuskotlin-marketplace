package ru.otus.otuskotlin.marketplace.backend.repository.sql.schema

import org.jetbrains.exposed.dao.id.UUIDTable

abstract class AdsTable(tableName: String): UUIDTable(tableName) {

    val avatar = varchar("avatar", 128)
    val title = text("title")
    val description = text("description")
//    abstract val avatar: Column<String>
//    abstract val title: Column<String>
//    abstract val description: Column<String>
//    val tagIds  определяется в DemandsTagsTable
//    val techDets = reference("tech_dets", TechDetsTable)
}
