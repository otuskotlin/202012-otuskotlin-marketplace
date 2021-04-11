package ru.otus.otuskotlin.marketplace.backend.app.kotless

import io.kotless.dsl.lang.http.Post
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.*
import ru.otus.otuskotlin.marketplace.business.logic.backend.ProposalCrud
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*

private val crud = ProposalCrud()

@Post("/proposal/list")
fun proposalList(): String? = handle { query: MpRequestProposalList? ->
    query?.also { setQuery(it) }
    crud.list(this)
    respondProposalList()
}

@Post("/proposal/create")
fun proposalCreate(): String? = handle { query: MpRequestProposalCreate? ->
    query?.also { setQuery(it) }
    crud.create(this)
    respondProposalCreate()
}

@Post("/proposal/read")
fun proposalRead(): String? = handle { query: MpRequestProposalRead? ->
    query?.also { setQuery(it) }
    crud.read(this)
    respondProposalRead()
}

@Post("/proposal/update")
fun proposalUpdate(): String? = handle { query: MpRequestProposalUpdate? ->
    query?.also { setQuery(it) }
    crud.update(this)
    respondProposalUpdate()
}

@Post("/proposal/delete")
fun proposalDelete(): String? = handle { query: MpRequestProposalDelete? ->
    query?.also { setQuery(it) }
    crud.delete(this)
    respondProposalDelete()
}

@Post("/proposal/offers")
fun proposalOffers(): String? = handle { query: MpRequestProposalOffers? ->
    query?.also { setQuery(it) }
    crud.offers(this)
    respondProposalOffers()
}
