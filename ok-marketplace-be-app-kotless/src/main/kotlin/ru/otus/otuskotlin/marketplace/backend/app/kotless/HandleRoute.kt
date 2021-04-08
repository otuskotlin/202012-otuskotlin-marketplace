package ru.otus.otuskotlin.marketplace.backend.app.kotless

import io.kotless.dsl.lang.KotlessContext
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import java.time.Instant
import java.util.*

val log: Logger = LoggerFactory.getLogger("DemandController")

@OptIn(InternalSerializationApi::class)
inline fun <reified T : Any, reified R : Any> handle(crossinline block: suspend MpBeContext.(T?) -> R): String? =
    KotlessContext.HTTP.request
        .myBody
        ?.let { q ->
            runBlocking {
                log.debug("Handling query: {}", q)
                val ctx = MpBeContext(
                    timeStarted = Instant.now(),
                    responseId = UUID.randomUUID().toString()
                )
                try {
                    val query = Json.decodeFromString(T::class.serializer(), q)
                    ctx.status = MpBeContextStatus.RUNNING
                    val result = ctx.block(query)
                    Json.encodeToString(R::class.serializer(), result).also { r ->
                        log.debug("Sending response: {}", r)
                    }
                } catch (e: Throwable) {
                    ctx.status = MpBeContextStatus.FAILING
                    ctx.errors.add(e.toModel())
                    val result = ctx.block(null)
                    Json.encodeToString(R::class.serializer(), result).also { r ->
                        log.debug("Sending response: {}", r)
                    }
                }
            }
        }
