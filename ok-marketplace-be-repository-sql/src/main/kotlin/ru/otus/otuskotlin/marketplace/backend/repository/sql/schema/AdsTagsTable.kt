package ru.otus.otuskotlin.marketplace.backend.repository.sql.schema

import org.jetbrains.exposed.dao.id.UUIDTable

open class AdsTagsTable(tableName: String, adsTable: AdsTable): UUIDTable(tableName) {
    val tagId = varchar("tag_id", 64).index()
    var ad = reference("ad_it", adsTable)
}
