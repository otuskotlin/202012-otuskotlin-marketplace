package ru.otus.otuskotlin.marketplace.backend.app.ktor.helpers

import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.DemandService
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.ProposalService
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*

suspend fun service(
    context: MpBeContext, 
    query: MpMessage?,
    demandService: DemandService,
    proposalService: ProposalService
): MpMessage = when(query) {
    is MpRequestDemandList ->   demandService.list(context, query)
    is MpRequestDemandCreate -> demandService.create(context, query)
    is MpRequestDemandRead ->   demandService.read(context, query)
    is MpRequestDemandUpdate -> demandService.update(context, query)
    is MpRequestDemandDelete -> demandService.delete(context, query)
    is MpRequestDemandOffers -> demandService.offers(context, query)

    is MpRequestProposalList ->   proposalService.list(context, query)
    is MpRequestProposalCreate -> proposalService.create(context, query)
    is MpRequestProposalRead ->   proposalService.read(context, query)
    is MpRequestProposalUpdate -> proposalService.update(context, query)
    is MpRequestProposalDelete -> proposalService.delete(context, query)
    is MpRequestProposalOffers -> proposalService.offers(context, query)

    else -> throw RuntimeException("It's unreal to reach this place")
    
}
