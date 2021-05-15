package ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers

import io.ktor.routing.*
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.DemandService
import ru.otus.otuskotlin.marketplace.backend.logging.mpLogger
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.*
import ru.otus.otuskotlin.marketplace.business.logic.backend.DemandCrud
import ru.otus.otuskotlin.marketplace.common.kmp.RestEndpoints
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*

private val logger = mpLogger("demandRouting")

fun Routing.demandRouting(service: DemandService) {
    post(RestEndpoints.demandList) {
        handleRoute<MpRequestDemandList,MpResponseDemandList>("demand-list", logger) { query ->
            service.list(this, query)
        }
    }
    post(RestEndpoints.demandCreate) {
        handleRoute<MpRequestDemandCreate,MpResponseDemandCreate>("demand-create", logger) { query ->
            service.create(this, query)
        }
    }
    post(RestEndpoints.demandRead) {
        handleRoute<MpRequestDemandRead,MpResponseDemandRead>("demand-read", logger) { query ->
            service.read(this, query)
        }
    }
    post(RestEndpoints.demandUpdate) {
        handleRoute<MpRequestDemandUpdate,MpResponseDemandUpdate>("demand-update", logger) { query ->
            service.update(this, query)
        }
    }
    post(RestEndpoints.demandDelete) {
        handleRoute<MpRequestDemandDelete,MpResponseDemandDelete>("demand-delete", logger) { query ->
            service.delete(this, query)
        }
    }
    post(RestEndpoints.demandOffers) {
        handleRoute<MpRequestDemandOffers,MpResponseDemandOffers>("demand-offers", logger) { query ->
            service.offers(this, query)
        }
    }
}

