package ru.otus.otuskotlin.marketplace.backend.app.ktor

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.websocket.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import pl.jutupe.ktor_rabbitmq.RabbitMQ
import pl.jutupe.ktor_rabbitmq.consume
import pl.jutupe.ktor_rabbitmq.publish
import pl.jutupe.ktor_rabbitmq.rabbitConsumer
import ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers.demandRouting
import ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers.mpWebsocket
import ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers.proposalRouting
import ru.otus.otuskotlin.marketplace.backend.app.ktor.helpers.service
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.DemandService
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.ProposalService
import ru.otus.otuskotlin.marketplace.business.logic.backend.DemandCrud
import ru.otus.otuskotlin.marketplace.business.logic.backend.ProposalCrud
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import java.time.Instant
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@OptIn(InternalSerializationApi::class)
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    val queueIn = "marketplaceQueueIn"
    val exchangeIn = "marketplaceExchange"
    val exchangeOut = "marketplaceExchange"

    val demandCrud = DemandCrud()
    val proposalCrud = ProposalCrud()
    val demandService = DemandService(demandCrud)
    val proposalService = ProposalService(proposalCrud)

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(WebSockets)
    install(ContentNegotiation) {
        json(
            contentType = ContentType.Application.Json,
            json = jsonConfig,
        )
    }
    install(RabbitMQ) {
        uri = "amqp://guest:guest@localhost:5672"
        connectionName = "Connection name"

        //serialize and deserialize functions are required
        serialize {
            when (it) {
                is MpMessage -> jsonConfig.encodeToString(MpMessage.serializer(), it).toByteArray()
                else -> jsonConfig.encodeToString(Any::class.serializer(), it).toByteArray()
            }
        }
        deserialize { bytes, type ->
            val jsonString = String(bytes, Charsets.UTF_8)
            jsonConfig.decodeFromString(type.serializer(), jsonString)
        }

        //example initialization logic
        initialize {
            exchangeDeclare(exchangeIn, "fanout", true)
            queueDeclare(queueIn, true, false, false, emptyMap())
            queueBind(
                queueIn,
                exchangeIn,
                "*"
            )
        }
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }

        demandRouting(demandService)
        proposalRouting(proposalService)

        mpWebsocket(demandService, proposalService)

        rabbitConsumer {
            consume<MpMessage>(queueIn) { consumerTag, query ->
                println("Consumed message $query, consumer tag: $consumerTag")
            }
        }
    }
}

