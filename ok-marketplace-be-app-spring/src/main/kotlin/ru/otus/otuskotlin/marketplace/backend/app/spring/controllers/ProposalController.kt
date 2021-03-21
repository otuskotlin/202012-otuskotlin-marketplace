package ru.otus.otuskotlin.marketplace.backend.app.spring.controllers

import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.ok
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.*
import ru.otus.otuskotlin.marketplace.business.logic.backend.ProposalCrud
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ErrorDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechDetsDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechParamDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandList
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*
import java.time.Instant

class ProposalController(val crud: ProposalCrud) {
    fun list(request: ServerRequest): ServerResponse =
        handleRoute<MpRequestProposalList, MpResponseProposalList>(request) { query ->
            query?.also { setQuery(it) }
            crud.list(this)
            respondProposalList()
        }

    fun create(request: ServerRequest): ServerResponse =
        handleRoute<MpRequestProposalCreate,MpResponseProposalCreate>(request) { query ->
            query?.also { setQuery(it) }
            crud.create(this)
            respondProposalCreate()
        }

    fun read(request: ServerRequest): ServerResponse =
        handleRoute<MpRequestProposalRead,MpResponseProposalRead>(request) { query ->
            query?.also { setQuery(it) }
            crud.read(this)
            respondProposalRead()
        }

    fun update(request: ServerRequest): ServerResponse =
        handleRoute<MpRequestProposalUpdate,MpResponseProposalUpdate>(request) { query ->
            query?.also { setQuery(it) }
            crud.update(this)
            respondProposalUpdate()
        }

    fun delete(request: ServerRequest): ServerResponse =
        handleRoute<MpRequestProposalDelete,MpResponseProposalDelete>(request) { query ->
            query?.also { setQuery(it) }
            crud.delete(this)
            respondProposalDelete()
        }

    fun offers(request: ServerRequest): ServerResponse =
        handleRoute<MpRequestProposalOffers,MpResponseProposalOffers>(request) { query ->
            query?.also { setQuery(it) }
            crud.offers(this)
            respondProposalOffers()
        }

}
