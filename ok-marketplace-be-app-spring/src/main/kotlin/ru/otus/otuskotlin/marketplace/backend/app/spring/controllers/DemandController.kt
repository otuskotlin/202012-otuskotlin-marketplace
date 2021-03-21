package ru.otus.otuskotlin.marketplace.backend.app.spring.controllers

import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.*
import ru.otus.otuskotlin.marketplace.business.logic.backend.DemandCrud
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*

class DemandController(val crud: DemandCrud) {
    fun list(request: ServerRequest): ServerResponse = handleRoute(request) { query: MpRequestDemandList? ->
            query?.also { setQuery(it) }
            crud.list(this)
            respondDemandList()
        }

    fun create(request: ServerRequest): ServerResponse = handleRoute(request) { query: MpRequestDemandCreate? ->
        query?.also { setQuery(it) }
        crud.create(this)
        respondDemandCreate()
    }

    fun read(request: ServerRequest): ServerResponse = handleRoute(request) { query: MpRequestDemandRead? ->
        query?.also { setQuery(it) }
        crud.read(this)
        respondDemandRead()
    }

    fun update(request: ServerRequest): ServerResponse = handleRoute(request) { query: MpRequestDemandUpdate? ->
        query?.also { setQuery(it) }
        crud.update(this)
        respondDemandUpdate()
    }

    fun delete(request: ServerRequest): ServerResponse = handleRoute(request) { query: MpRequestDemandDelete? ->
        query?.also { setQuery(it) }
        crud.delete(this)
        respondDemandDelete()
    }

    fun offers(request: ServerRequest): ServerResponse = handleRoute(request) { query: MpRequestDemandOffers? ->
        query?.also { setQuery(it) }
        crud.offers(this)
        respondDemandOffers()
    }

}
