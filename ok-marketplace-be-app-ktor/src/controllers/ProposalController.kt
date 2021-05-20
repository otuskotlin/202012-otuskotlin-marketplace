package ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers

import ru.otus.otuskotlin.marketplace.backend.app.ktor.helpers.handleRoute
import io.ktor.routing.*
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.ProposalService
import ru.otus.otuskotlin.marketplace.backend.logging.mpLogger
import ru.otus.otuskotlin.marketplace.common.kmp.RestEndpoints
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*

private val logger = mpLogger(Routing::proposalRouting::class.java)

fun Routing.proposalRouting(service: ProposalService) {
    post(RestEndpoints.proposalList) {
        handleRoute<MpRequestProposalList,MpResponseProposalList>("proposal-list", logger) { query ->
            service.list(this, query)
        }
    }
    post(RestEndpoints.proposalCreate) {
        handleRoute<MpRequestProposalCreate,MpResponseProposalCreate>("proposal-create", logger) { query ->
            service.create(this, query)
        }
    }
    post(RestEndpoints.proposalRead) {
        handleRoute<MpRequestProposalRead,MpResponseProposalRead>("proposal-read", logger) { query ->
            service.read(this, query)
        }
    }
    post(RestEndpoints.proposalUpdate) {
        handleRoute<MpRequestProposalUpdate,MpResponseProposalUpdate>("proposal-update", logger) { query ->
            service.update(this, query)
        }
    }
    post(RestEndpoints.proposalDelete) {
        handleRoute<MpRequestProposalDelete,MpResponseProposalDelete>("proposal-delete", logger) { query ->
            service.delete(this, query)
        }
    }
    post(RestEndpoints.proposalOffers) {
        handleRoute<MpRequestProposalOffers,MpResponseProposalOffers>("proposal-offers", logger) { query ->
            service.offers(this, query)
        }
    }
}

