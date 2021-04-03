package ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers

import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import ru.otus.otuskotlin.marketplace.backend.app.ktor.helpers.service
import ru.otus.otuskotlin.marketplace.backend.app.ktor.jsonConfig
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.DemandService
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.ProposalService
import ru.otus.otuskotlin.marketplace.backend.app.ktor.toModel
import ru.otus.otuskotlin.marketplace.business.logic.backend.DemandCrud
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import java.time.Instant
import java.util.*

@OptIn(InternalSerializationApi::class)
fun Routing.mpWebsocket(
    demandService: DemandService,
    proposalService: ProposalService
) {
    webSocket("/ws") { // websocketSession
        for (frame in incoming) {
            if (frame is Frame.Text) {
                val ctx = MpBeContext(
                    responseId = UUID.randomUUID().toString(),
                    timeStarted = Instant.now()
                )
                try {
                    val requestJson = frame.readText()
                    val query = jsonConfig.decodeFromString(MpMessage.serializer(), requestJson)
                    ctx.status = MpBeContextStatus.RUNNING
                    val response = service(
                        context = ctx,
                        query = query,
                        demandService = demandService,
                        proposalService = proposalService
                    )
                    val respJson = jsonConfig.encodeToString(MpMessage::class.serializer(), response)
                    outgoing.send(Frame.Text(respJson))
                } catch (e: ClosedReceiveChannelException) {
                } catch (e: Throwable) {
                    e.printStackTrace()
                    ctx.status = MpBeContextStatus.FAILING
                    ctx.errors.add(e.toModel())
                    val response = service(
                        context = ctx,
                        query = null,
                        demandService = demandService,
                        proposalService = proposalService
                    )
                    val respJson = jsonConfig.encodeToString(MpMessage::class.serializer(), response)
                    outgoing.send(Frame.Text(respJson))
                }

                val responseJson = ""
//                    if (text.equals("bye", ignoreCase = true)) {
//                        close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
//                    }
            }
        }
    }
}
