package ru.otus.otuskotlin.marketplace.backend.app.spring.controllers

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import org.springframework.http.MediaType
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.body
import ru.otus.otuskotlin.marketplace.backend.app.spring.jsonConfig
import ru.otus.otuskotlin.marketplace.backend.app.spring.toModel
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import java.time.Instant
import java.util.*

@OptIn(InternalSerializationApi::class)
inline fun <reified T : MpMessage, reified U : MpMessage> handleRoute(
    request: ServerRequest,
    crossinline block: suspend MpBeContext.(T?) -> U
): ServerResponse = runBlocking {
    val ctx = MpBeContext(
        responseId = UUID.randomUUID().toString(),
        timeStarted = Instant.now()
    )
    try {
        val queryJson = request.body<String>()
        println("QUERY: $queryJson")
        val query = jsonConfig.decodeFromString(T::class.serializer(), queryJson) as T
        ctx.status = MpBeContextStatus.RUNNING
        val response = ctx.block(query)
        val responseJson = jsonConfig.encodeToString(U::class.serializer(), response)
        println("RESPONSE: $responseJson")
        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(responseJson)
    } catch (e: Throwable) {
        ctx.status = MpBeContextStatus.FAILING
        ctx.errors.add(e.toModel())
        val response = ctx.block(null)
        val responseJson = jsonConfig.encodeToString(U::class.serializer(), response)
        println("RESPONSE ERR: $responseJson")
        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(responseJson)
    }
}
