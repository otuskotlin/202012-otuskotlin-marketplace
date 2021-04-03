package ru.otus.otuskotlin.marketplace.backend.app.ktor.services

import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.*
import ru.otus.otuskotlin.marketplace.business.logic.backend.DemandCrud
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*

class DemandService(private val crud: DemandCrud) {

    suspend fun list(context: MpBeContext, query: MpRequestDemandList?): MpResponseDemandList = with (context) {
        query?.also { setQuery(it) }
        crud.list(this)
        return respondDemandList()
    }

    suspend fun create(context: MpBeContext, query: MpRequestDemandCreate?): MpResponseDemandCreate = with (context) {
        query?.also { setQuery(it) }
        crud.create(this)
        return respondDemandCreate()
    }

    suspend fun read(context: MpBeContext, query: MpRequestDemandRead?): MpResponseDemandRead = with (context) {
        query?.also { setQuery(it) }
        crud.read(this)
        return respondDemandRead()
    }

    suspend fun update(context: MpBeContext, query: MpRequestDemandUpdate?): MpResponseDemandUpdate = with (context) {
        query?.also { setQuery(it) }
        crud.update(this)
        return respondDemandUpdate()
    }

    suspend fun delete(context: MpBeContext, query: MpRequestDemandDelete?): MpResponseDemandDelete = with (context) {
        query?.also { setQuery(it) }
        crud.delete(this)
        return respondDemandDelete()
    }

    suspend fun offers(context: MpBeContext, query: MpRequestDemandOffers?): MpResponseDemandOffers = with (context) {
        query?.also { setQuery(it) }
        crud.offers(this)
        return respondDemandOffers()
    }

}
