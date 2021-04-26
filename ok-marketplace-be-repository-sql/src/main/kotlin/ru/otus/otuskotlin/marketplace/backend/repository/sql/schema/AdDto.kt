package ru.otus.otuskotlin.marketplace.backend.repository.sql.schema

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.otus.otuskotlin.marketplace.common.backend.models.IMpItemModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import java.util.*

open class AdDto<T: IMpItemModel>(
    id: EntityID<UUID>,
    adsTable: AdsTable,
//    adsTagsTable: AdsTagsTable,
): UUIDEntity(id) {
    var avatar by adsTable.avatar
    var title by adsTable.title
    var description by adsTable.description
//    val tagIds by DemandTagDto referrersOn DemandsTagsTable.demand

    fun of(model: T) {
        avatar = model.avatar
        title = model.title
        description = model.description
    }

}
