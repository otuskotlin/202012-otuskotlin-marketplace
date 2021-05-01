package ru.otus.otuskotlin.marketplace.backend.app.kotless

import io.kotless.dsl.lang.http.Post
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.respondDemandCreate
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.respondDemandDelete
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.respondDemandList
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.respondDemandOffers
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.respondDemandRead
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.respondDemandUpdate
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.setQuery
import ru.otus.otuskotlin.marketplace.backend.repository.dynamodb.DemandRepositoryDynamoDB
import ru.otus.otuskotlin.marketplace.backend.repository.dynamodb.ProposalRepositoryDynamoDB
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.demands.DemandRepoInMemory
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.proposals.ProposalRepoInMemory
import ru.otus.otuskotlin.marketplace.business.logic.backend.DemandCrud
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandCreate
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandDelete
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandList
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandOffers
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandRead
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandUpdate
import kotlin.time.ExperimentalTime
import kotlin.time.hours

@OptIn(ExperimentalTime::class)
private val crud = DemandCrud(
    demandRepoProd = DemandRepositoryDynamoDB(),
    demandRepoTest = DemandRepoInMemory(ttl = 2.hours),
    proposalRepoProd = ProposalRepositoryDynamoDB(),
    proposalRepoTest = ProposalRepoInMemory(ttl = 2.hours)
)

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
