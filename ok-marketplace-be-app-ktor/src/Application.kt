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
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import pl.jutupe.ktor_rabbitmq.RabbitMQ
import ru.datana.smart.common.ktor.kafka.KtorKafkaConsumer
import ru.datana.smart.common.ktor.kafka.kafka
import ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers.demandRouting
import ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers.mpWebsocket
import ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers.proposalRouting
import ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers.rabbitMq
import ru.otus.otuskotlin.marketplace.backend.app.ktor.helpers.service
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.DemandService
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.ProposalService
import ru.otus.otuskotlin.marketplace.business.logic.backend.DemandCrud
import ru.otus.otuskotlin.marketplace.business.logic.backend.ProposalCrud
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpResponse
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import java.time.Instant
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@OptIn(InternalSerializationApi::class)
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(
    testing: Boolean = false,
    kafkaTestConsumer: Consumer<String, String>? = null,
    kafkaTestProducer: Producer<String, String>? = null
) {

//    val queueIn by lazy { environment.config.property("marketplace.rabbitmq.queueIn").getString() }
//    val exchangeIn by lazy { environment.config.property("marketplace.rabbitmq.exchangeIn").getString() }
//    val exchangeOut by lazy { environment.config.property("marketplace.rabbitmq.exchangeOut").getString() }
//    val rabbitMqEndpoint by lazy { environment.config.property("marketplace.rabbitmq.endpoint").getString() }
    val topicIn by lazy { environment.config.property("marketplace.kafka.topicIn").getString() }
    val topicOut by lazy { environment.config.property("marketplace.kafka.topicOut").getString() }

    val demandCrud = DemandCrud()
    val proposalCrud = ProposalCrud()
    val demandService = DemandService(demandCrud)
    val proposalService = ProposalService(proposalCrud)
    val kafkaProducer = kafkaTestProducer

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

    install(KtorKafkaConsumer)
    install(WebSockets)
    install(ContentNegotiation) {
        json(
            contentType = ContentType.Application.Json,
            json = jsonConfig,
        )
    }
//    install(RabbitMQ) {
//        uri = rabbitMqEndpoint
//        connectionName = "Connection name"
//
//        //serialize and deserialize functions are required
//        serialize {
//            when (it) {
//                is MpMessage -> jsonConfig.encodeToString(MpMessage.serializer(), it).toByteArray()
//                else -> jsonConfig.encodeToString(Any::class.serializer(), it).toByteArray()
//            }
//        }
//        deserialize { bytes, type ->
//            val jsonString = String(bytes, Charsets.UTF_8)
//            jsonConfig.decodeFromString(type.serializer(), jsonString)
//        }
//
//        //example initialization logic
//        initialize {
//            exchangeDeclare(exchangeIn, "fanout", true)
//            exchangeDeclare(exchangeOut, "fanout", true)
//            queueDeclare(queueIn, true, false, false, emptyMap())
//            queueBind(
//                queueIn,
//                exchangeIn,
//                "*"
//            )
//        }
//    }

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

//        rabbitMq(
//            queueIn = queueIn,
//            exchangeOut = exchangeOut,
//            demandService = demandService,
//            proposalService = proposalService
//        )

        kafka<String, String> {
            keyDeserializer = StringDeserializer::class.java
            valDeserializer = StringDeserializer::class.java
            consumer = kafkaTestConsumer

            topic(topicIn) {
                for (item in items.items) {
                    val ctx = MpBeContext(
                        responseId = UUID.randomUUID().toString(),
                        timeStarted = Instant.now(),
                    )
                    try {
                        ctx.status = MpBeContextStatus.RUNNING
                        val query = jsonConfig.decodeFromString(MpMessage.serializer(), item.value)
                        service(
                            context = ctx,
                            query = query,
                            demandService = demandService,
                            proposalService = proposalService
                        )?.also {
                            kafkaProducer?.send(
                                ProducerRecord(
                                    topicOut,
                                    (it as IMpResponse).responseId,
                                    jsonConfig.encodeToString(MpMessage.serializer(), it)
                                )
                            )
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
                                kafkaProducer?.send(
                                    ProducerRecord(
                                        topicOut,
                                        (it as IMpResponse).responseId,
                                        jsonConfig.encodeToString(MpMessage.serializer(), it)
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
