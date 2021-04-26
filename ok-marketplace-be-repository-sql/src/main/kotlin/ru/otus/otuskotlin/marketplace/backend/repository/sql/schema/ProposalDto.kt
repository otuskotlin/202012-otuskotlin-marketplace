package ru.otus.otuskotlin.marketplace.backend.repository.sql.schema

import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel
import java.util.*

class ProposalDto(id: EntityID<UUID>): AdDto<MpProposalModel>(id, ProposalsTable) {

    fun toModel() = MpProposalModel(
        id = MpProposalIdModel(id.value.toString()),
        avatar = avatar?: "",
        title = title?: "",
        description = description?: "",
//        tagIds = tagIds.limit(100).map { it.tagId }.toMutableSet(),
    )

    companion object: UUIDEntityClass<ProposalDto>(ProposalsTable) {
    }
}
