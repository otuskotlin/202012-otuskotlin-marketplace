package ru.otus.otuskotlin.marketplace.backend.app.ktor.services

import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.*
import ru.otus.otuskotlin.marketplace.business.logic.backend.ProposalCrud
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*

class ProposalService(private val crud: ProposalCrud) {

    suspend fun list(context: MpBeContext, query: MpRequestProposalList?): MpResponseProposalList = with (context) {
        query?.also { setQuery(it) }
        crud.list(this)
        return respondProposalList()
    }

    suspend fun create(context: MpBeContext, query: MpRequestProposalCreate?): MpResponseProposalCreate = with (context) {
        query?.also { setQuery(it) }
        crud.create(this)
        return respondProposalCreate()
    }

    suspend fun read(context: MpBeContext, query: MpRequestProposalRead?): MpResponseProposalRead = with (context) {
        query?.also { setQuery(it) }
        crud.read(this)
        return respondProposalRead()
    }

    suspend fun update(context: MpBeContext, query: MpRequestProposalUpdate?): MpResponseProposalUpdate = with (context) {
        query?.also { setQuery(it) }
        crud.update(this)
        return respondProposalUpdate()
    }

    suspend fun delete(context: MpBeContext, query: MpRequestProposalDelete?): MpResponseProposalDelete = with (context) {
        query?.also { setQuery(it) }
        crud.delete(this)
        return respondProposalDelete()
    }

    suspend fun offers(context: MpBeContext, query: MpRequestProposalOffers?): MpResponseProposalOffers = with (context) {
        query?.also { setQuery(it) }
        crud.offers(this)
        return respondProposalOffers()
    }

}
