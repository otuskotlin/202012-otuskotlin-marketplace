package ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers

import io.ktor.routing.*
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.*
import ru.otus.otuskotlin.marketplace.business.logic.backend.ProposalCrud
import ru.otus.otuskotlin.marketplace.common.kmp.RestEndpoints
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*

fun Routing.proposalRouting(crud: ProposalCrud) {
    post(RestEndpoints.proposalList) {
        handleRoute<MpRequestProposalList,MpResponseProposalList> { query ->
            query?.also { setQuery(it) }
            crud.list(this)
            respondProposalList()
        }
    }
    post(RestEndpoints.proposalCreate) {
        handleRoute<MpRequestProposalCreate,MpResponseProposalCreate> { query ->
            query?.also { setQuery(it) }
            crud.create(this)
            respondProposalCreate()
        }
    }
    post(RestEndpoints.proposalRead) {
        handleRoute<MpRequestProposalRead,MpResponseProposalRead> { query ->
            query?.also { setQuery(it) }
            crud.read(this)
            respondProposalRead()
        }
    }
    post(RestEndpoints.proposalUpdate) {
        handleRoute<MpRequestProposalUpdate,MpResponseProposalUpdate> { query ->
            query?.also { setQuery(it) }
            crud.update(this)
            respondProposalUpdate()
        }
    }
    post(RestEndpoints.proposalDelete) {
        handleRoute<MpRequestProposalDelete,MpResponseProposalDelete> { query ->
            query?.also { setQuery(it) }
            crud.delete(this)
            respondProposalDelete()
        }
    }
    post(RestEndpoints.proposalOffers) {
        handleRoute<MpRequestProposalOffersList,MpResponseProposalOffersList> { query ->
            query?.also { setQuery(it) }
            crud.offers(this)
            respondProposalOffers()
        }
    }
}

