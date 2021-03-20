package ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers

import io.ktor.routing.*
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.*
import ru.otus.otuskotlin.marketplace.business.logic.backend.DemandCrud
import ru.otus.otuskotlin.marketplace.common.kmp.RestEndpoints
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*

fun Routing.demandRouting(crud: DemandCrud) {
    post(RestEndpoints.demandList) {
        handleRoute<MpRequestDemandList,MpResponseDemandList> { query ->
            query?.also { setQuery(it) }
            crud.list(this)
            respondDemandList()
        }
    }
    post(RestEndpoints.demandCreate) {
        handleRoute<MpRequestDemandCreate,MpResponseDemandCreate> { query ->
            query?.also { setQuery(it) }
            crud.create(this)
            respondDemandCreate()
        }
    }
    post(RestEndpoints.demandRead) {
        handleRoute<MpRequestDemandRead,MpResponseDemandRead> { query ->
            println("QUERY: $query")
            query?.also { setQuery(it) }
            crud.read(this)
            respondDemandRead()
        }
    }
    post(RestEndpoints.demandUpdate) {
        handleRoute<MpRequestDemandUpdate,MpResponseDemandUpdate> { query ->
            query?.also { setQuery(it) }
            crud.update(this)
            respondDemandUpdate()
        }
    }
    post(RestEndpoints.demandDelete) {
        handleRoute<MpRequestDemandDelete,MpResponseDemandDelete> { query ->
            query?.also { setQuery(it) }
            crud.delete(this)
            respondDemandDelete()
        }
    }
    post(RestEndpoints.demandOffers) {
        handleRoute<MpRequestDemandOffersList,MpResponseDemandOffersList> { query ->
            query?.also { setQuery(it) }
            crud.offers(this)
            respondDemandOffers()
        }
    }
}

