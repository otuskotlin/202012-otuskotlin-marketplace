package ru.otus.otuskotlin.marketplace.backend.app.ktor.ru.otus.otuskotlin.marketplace.backend.app.ktor.helpers

import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.isActive
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.DemandService
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.ProposalService
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*

suspend fun service(
    context: MpBeContext,
    query: MpMessage?,
    demandService: DemandService,
    proposalService: ProposalService
): MpMessage? = when (query) {
    is MpRequestDemandList -> demandService.list(context, query)
    is MpRequestDemandCreate -> demandService.create(context, query)
    is MpRequestDemandRead -> demandService.read(context, query)
    is MpRequestDemandUpdate -> demandService.update(context, query)
    is MpRequestDemandDelete -> demandService.delete(context, query)
    is MpRequestDemandOffers -> demandService.offers(context, query)

    is MpRequestProposalList -> proposalService.list(context, query)
    is MpRequestProposalCreate -> proposalService.create(context, query)
    is MpRequestProposalRead -> proposalService.read(context, query)
    is MpRequestProposalUpdate -> proposalService.update(context, query)
    is MpRequestProposalDelete -> proposalService.delete(context, query)
    is MpRequestProposalOffers -> proposalService.offers(context, query)

    else ->
        // В дальнейшем здесь должен оказаться чейн обработки ошибок, и других событий
        when {
            context.status == MpBeContextStatus.FAILING -> demandService.list(context, null)
            // Если содзана новая сессия
            (context.userSession.fwSession as? WebSocketSession)?.isActive == true -> demandService.list(
                context,
                MpRequestDemandList()
            )
            // Если удалена сессия
            (context.userSession.fwSession as? WebSocketSession)?.isActive == false -> null
            else -> null
        }

}
