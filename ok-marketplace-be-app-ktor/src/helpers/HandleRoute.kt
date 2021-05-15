package ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import org.slf4j.event.Level
import ru.otus.otuskotlin.marketplace.backend.app.ktor.jsonConfig
import ru.otus.otuskotlin.marketplace.backend.app.ktor.toModel
import ru.otus.otuskotlin.marketplace.backend.logging.DefaultMarker
import ru.otus.otuskotlin.marketplace.backend.logging.MpLogContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpRequest
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import java.time.Instant
import java.util.*

@OptIn(InternalSerializationApi::class)
suspend inline fun <reified T : IMpRequest, reified U : MpMessage> PipelineContext<Unit, ApplicationCall>.handleRoute(
    logId: String,
    logger: MpLogContext,
    crossinline block: suspend MpBeContext.(T?) -> U
) {
    val ctx = MpBeContext(
        timeStarted = Instant.now(),
        responseId = UUID.randomUUID().toString()
    )
    try {
        logger.doWithLogging(logId){
            val query = call.receive<MpMessage>() as T
            logger.log(
                msg = "Request for $logId, query = {}",
                level = Level.INFO,
                data = query,
            )
            ctx.status = MpBeContextStatus.RUNNING
            val response = ctx.block(query)
            val respJson = jsonConfig.encodeToString(MpMessage::class.serializer(), response)
            call.respondText(respJson, contentType = ContentType.parse("application/json"))
            logger.log(
                msg = "Respond for $logId, response = {}",
                level = Level.INFO,
                data = response,
            )
        }
    } catch (e: Throwable) {
        ctx.status = MpBeContextStatus.FAILING
        ctx.errors.add(e.toModel())
        val response = ctx.block(null)
        val respJson = jsonConfig.encodeToString(MpMessage::class.serializer(), response)
        call.respondText(respJson, contentType = ContentType.parse("application/json"))
    }
}
