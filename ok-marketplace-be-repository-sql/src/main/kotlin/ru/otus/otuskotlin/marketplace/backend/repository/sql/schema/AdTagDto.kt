package ru.otus.otuskotlin.marketplace.backend.repository.sql.schema

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.otus.otuskotlin.marketplace.common.backend.models.IMpItemIdModel
import java.util.*

open class AdTagDto<D: AdDto<*,*>>(id: EntityID<UUID>, adsTagsTable: AdsTagsTable, adCompanion: UUIDEntityClass<D>): UUIDEntity(id) {
    var tagId by adsTagsTable.tagId
    var ad by adCompanion referencedOn adsTagsTable.ad
}
