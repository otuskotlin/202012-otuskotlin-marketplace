package ru.otus.otuskotlin.marketplace.backend.app.kotless

import io.kotless.dsl.lang.http.Post
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.*
import ru.otus.otuskotlin.marketplace.business.logic.backend.DemandCrud
import ru.otus.otuskotlin.marketplace.common.kmp.RestEndpoints
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*

private val crud = DemandCrud()

@Post("/demand/list")
fun demandList(): String? = handle { query: MpRequestDemandList? ->
    query?.also { setQuery(it) }
    crud.list(this)
    respondDemandList()
}

@Post("/demand/create")
fun demandCreate(): String? = handle { query: MpRequestDemandCreate? ->
    query?.also { setQuery(it) }
    crud.create(this)
    respondDemandCreate()
}

@Post("/demand/read")
fun demandRead(): String? = handle { query: MpRequestDemandRead? ->
    query?.also { setQuery(it) }
    crud.read(this)
    respondDemandRead()
}

@Post("/demand/update")
fun demandUpdate(): String? = handle { query: MpRequestDemandUpdate? ->
    query?.also { setQuery(it) }
    crud.update(this)
    respondDemandUpdate()
}

@Post("/demand/delete")
fun demandDelete(): String? = handle { query: MpRequestDemandDelete? ->
    query?.also { setQuery(it) }
    crud.delete(this)
    respondDemandDelete()
}

@Post("/demand/offers")
fun demandOffers(): String? = handle { query: MpRequestDemandOffers? ->
    query?.also { setQuery(it) }
    crud.offers(this)
    respondDemandOffers()
}
