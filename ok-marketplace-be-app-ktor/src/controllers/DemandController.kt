package ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.DemandService
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.*
import ru.otus.otuskotlin.marketplace.business.logic.backend.DemandCrud
import ru.otus.otuskotlin.marketplace.common.kmp.RestEndpoints
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*

fun Routing.demandRouting(service: DemandService, authOff: Boolean = false) {
    authenticate("auth-jwt", optional = authOff) {
        post(RestEndpoints.demandList) {
            handleRoute<MpRequestDemandList, MpResponseDemandList> { query ->
                useAuth = ! authOff
                service.list(this, query)
            }
        }
        post(RestEndpoints.demandCreate) {
            handleRoute<MpRequestDemandCreate, MpResponseDemandCreate> { query ->
                useAuth = ! authOff
                service.create(this, query)
            }
        }
        post(RestEndpoints.demandRead) {
            println(call.authentication.principal)
            handleRoute<MpRequestDemandRead, MpResponseDemandRead> { query ->
                useAuth = ! authOff
                service.read(this, query)
            }
        }
        post(RestEndpoints.demandUpdate) {
            handleRoute<MpRequestDemandUpdate, MpResponseDemandUpdate> { query ->
                useAuth = ! authOff
                service.update(this, query)
            }
        }
        post(RestEndpoints.demandDelete) {
            handleRoute<MpRequestDemandDelete, MpResponseDemandDelete> { query ->
                useAuth = ! authOff
                service.delete(this, query)
            }
        }
        post(RestEndpoints.demandOffers) {
            handleRoute<MpRequestDemandOffers, MpResponseDemandOffers> { query ->
                useAuth = ! authOff
                service.offers(this, query)
            }
        }
    }
}

