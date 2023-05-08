package ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers

import io.ktor.auth.*
import io.ktor.routing.*
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.ProposalService
import ru.otus.otuskotlin.marketplace.common.kmp.RestEndpoints
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*

fun Routing.proposalRouting(service: ProposalService, authOff: Boolean = false) {
    authenticate("auth-jwt", optional = authOff) {
        post(RestEndpoints.proposalList) {
            handleRoute<MpRequestProposalList, MpResponseProposalList> { query ->
                service.list(this, query)
            }
        }
        post(RestEndpoints.proposalCreate) {
            handleRoute<MpRequestProposalCreate, MpResponseProposalCreate> { query ->
                service.create(this, query)
            }
        }
        post(RestEndpoints.proposalRead) {
            handleRoute<MpRequestProposalRead, MpResponseProposalRead> { query ->
                service.read(this, query)
            }
        }
        post(RestEndpoints.proposalUpdate) {
            handleRoute<MpRequestProposalUpdate, MpResponseProposalUpdate> { query ->
                service.update(this, query)
            }
        }
        post(RestEndpoints.proposalDelete) {
            handleRoute<MpRequestProposalDelete, MpResponseProposalDelete> { query ->
                service.delete(this, query)
            }
        }
        post(RestEndpoints.proposalOffers) {
            handleRoute<MpRequestProposalOffers, MpResponseProposalOffers> { query ->
                service.offers(this, query)
            }
        }
    }
}
