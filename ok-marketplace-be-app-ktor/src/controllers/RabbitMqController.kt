package ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers

import io.ktor.application.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer
import pl.jutupe.ktor_rabbitmq.RabbitMQ
import pl.jutupe.ktor_rabbitmq.consume
import pl.jutupe.ktor_rabbitmq.publish
import pl.jutupe.ktor_rabbitmq.rabbitConsumer
import ru.otus.otuskotlin.marketplace.backend.app.ktor.helpers.service
import ru.otus.otuskotlin.marketplace.backend.app.ktor.jsonConfig
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.DemandService
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.ProposalService
import ru.otus.otuskotlin.marketplace.backend.app.ktor.toModel
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import java.time.Instant
import java.util.*


@OptIn(InternalSerializationApi::class)
fun Application.rabbitMqEndpoints(
    rabbitMqEndpoint: String,
    demandService: DemandService,
    proposalService: ProposalService,
) {
    val queueIn by lazy { environment.config.property("marketplace.rabbitmq.queueIn").getString() }
    val exchangeIn by lazy { environment.config.property("marketplace.rabbitmq.exchangeIn").getString() }
    val exchangeOut by lazy { environment.config.property("marketplace.rabbitmq.exchangeOut").getString() }

    install(RabbitMQ) {
        uri = rabbitMqEndpoint
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
            exchangeDeclare(exchangeOut, "fanout", true)
            queueDeclare(queueIn, true, false, false, emptyMap())
            queueBind(
                queueIn,
                exchangeIn,
                "*"
            )
        }
    }
    rabbitMq(
        queueIn = queueIn,
        exchangeOut = exchangeOut,
        demandService = demandService,
        proposalService = proposalService
    )
}

fun Application.rabbitMq(
    queueIn: String,
    exchangeOut: String,
    demandService: DemandService,
    proposalService: ProposalService
) {
    rabbitConsumer {
        consume<MpMessage>(queueIn) { consumerTag, query ->
            println("Consumed message $query, consumer tag: $consumerTag")
            val ctx = MpBeContext(
                responseId = UUID.randomUUID().toString(),
                timeStarted = Instant.now(),
            )
            try {
                ctx.status = MpBeContextStatus.RUNNING
                runBlocking {
                    service(
                        context = ctx,
                        query = query,
                        demandService = demandService,
                        proposalService = proposalService
                    )?.also {
                        publish(exchangeOut, "", null, it)
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                ctx.status = MpBeContextStatus.FAILING
                ctx.errors.add(e.toModel())
                runBlocking {
                    service(
                        context = ctx,
                        query = null,
                        demandService = demandService,
                        proposalService = proposalService
                    )?.also {
                        publish(exchangeOut, "", null, it)
                    }
                }
            }
        }
    }
}
