package ru.otus.otuskotlin.marketplace.backend.repository.sql.schema

import org.jetbrains.exposed.dao.id.UUIDTable

object ProposalsTagsTable: AdsTagsTable("proposals-tags", ProposalsTable)
