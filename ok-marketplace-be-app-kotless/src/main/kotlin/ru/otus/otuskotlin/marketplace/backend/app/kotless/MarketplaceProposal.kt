package ru.otus.otuskotlin.marketplace.backend.app.kotless

import io.kotless.dsl.lang.http.Post
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.respondProposalCreate
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.respondProposalDelete
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.respondProposalList
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.respondProposalOffers
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.respondProposalRead
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.respondProposalUpdate
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.setQuery
import ru.otus.otuskotlin.marketplace.backend.repository.dynamodb.DemandRepositoryDynamoDB
import ru.otus.otuskotlin.marketplace.backend.repository.dynamodb.ProposalRepositoryDynamoDB
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.demands.DemandRepoInMemory
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.proposals.ProposalRepoInMemory
import ru.otus.otuskotlin.marketplace.business.logic.backend.ProposalCrud
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpRequestProposalCreate
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpRequestProposalDelete
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpRequestProposalList
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpRequestProposalOffers
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpRequestProposalRead
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpRequestProposalUpdate
import kotlin.time.ExperimentalTime
import kotlin.time.hours

@OptIn(ExperimentalTime::class)
private val crud = ProposalCrud(
    demandRepoProd = DemandRepositoryDynamoDB(),
    demandRepoTest = DemandRepoInMemory(ttl = 2.hours),
    proposalRepoProd = ProposalRepositoryDynamoDB(),
    proposalRepoTest = ProposalRepoInMemory(ttl = 2.hours)
)

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
