package ru.otus.otuskotlin.marketplace.backend.repository.sql.schema

import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ProposalTagDto(id: EntityID<UUID>) : AdTagDto<ProposalDto>(id, ProposalsTagsTable, ProposalDto) {
    var proposal
        get() = ad
        set(value) {
            ad = value
        }

    companion object : UUIDEntityClass<ProposalTagDto>(ProposalsTagsTable) {
    }
}
