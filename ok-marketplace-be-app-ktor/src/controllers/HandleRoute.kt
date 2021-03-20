package ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import ru.otus.otuskotlin.marketplace.backend.app.ktor.jsonConfig
import ru.otus.otuskotlin.marketplace.backend.app.ktor.toModel
import ru.otus.otuskotlin.marketplace.backend.mappers.kmp.setException
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpRequest
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import java.time.Instant
import java.util.*

@OptIn(InternalSerializationApi::class)
suspend inline fun <reified T : IMpRequest, reified U : MpMessage> PipelineContext<Unit, ApplicationCall>.handleRoute(block: suspend MpBeContext.(T?) -> U) {
    val ctx = MpBeContext(
        responseId = UUID.randomUUID().toString(),
        timeStarted = Instant.now()
    )
    try {
        val query = call.receive<MpMessage>() as T
        println("QUERY: $query")
        ctx.status = MpBeContextStatus.RUNNING
        val response = ctx.block(query)
        val respJson = jsonConfig.encodeToString(MpMessage::class.serializer(), response)
        println("RESPONSE: $respJson")
        call.respondText(respJson, contentType = ContentType.parse("application/json"))
    } catch (e: Throwable) {
        ctx.status = MpBeContextStatus.FAILING
        ctx.errors.add(e.toModel())
        val response = ctx.block(null)
        val respJson = jsonConfig.encodeToString(MpMessage::class.serializer(), response)
        println("RESPONSE: $respJson")
        call.respondText(respJson, contentType = ContentType.parse("application/json"))
    }
}
