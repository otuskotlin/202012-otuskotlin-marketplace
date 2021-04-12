package ru.otus.otuskotlin.marketplace.backend.app.ktor

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.producer.Producer
import ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers.*
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.DemandService
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.ProposalService
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.demands.DemandRepoInMemory
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.proposals.ProposalRepoInMemory
import ru.otus.otuskotlin.marketplace.business.logic.backend.DemandCrud
import ru.otus.otuskotlin.marketplace.business.logic.backend.ProposalCrud
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IDemandRepository
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IProposalRepository
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@OptIn(ExperimentalTime::class)
@Suppress("unused") // Referenced in application.conf
fun Application.module(
    testing: Boolean = false,
    kafkaTestConsumer: Consumer<String, String>? = null,
    kafkaTestProducer: Producer<String, String>? = null,
    testDemandRepo: IDemandRepository? = null,
    testProposalRepo: IProposalRepository? = null,
) {

    val demandRepoTest = testDemandRepo ?: DemandRepoInMemory(ttl = 2.toDuration(DurationUnit.HOURS))
    val proposalRepoTest = testProposalRepo ?: ProposalRepoInMemory(ttl = 2.toDuration(DurationUnit.HOURS))
    val demandCrud = DemandCrud(
        demandRepoTest = demandRepoTest,
    )
    val proposalCrud = ProposalCrud(
        proposalRepoTest = proposalRepoTest
    )
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

    install(ContentNegotiation) {
        json(
            contentType = ContentType.Application.Json,
            json = jsonConfig,
        )
    }

    // Подключаем Websocket
    websocketEndpoints(
        demandService = demandService,
        proposalService = proposalService
    )

    // Подключаем RabbitMQ
    val rabbitMqEndpoint = environment.config.propertyOrNull("marketplace.rabbitmq.endpoint")?.getString()
    if (rabbitMqEndpoint != null) {
        rabbitMqEndpoints(
            rabbitMqEndpoint = rabbitMqEndpoint,
            demandService = demandService,
            proposalService = proposalService
        )
    }

    // Подключаем Kafka
    val brokers = environment.config.propertyOrNull("marketplace.kafka.brokers")?.getString()
    if (brokers != null) {
        kafkaEndpoints(
            brokers = brokers,
            kafkaConsumer = kafkaTestConsumer,
            kafkaProducer = kafkaTestProducer,
            demandService = demandService,
            proposalService = proposalService
        )
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

    }
}
