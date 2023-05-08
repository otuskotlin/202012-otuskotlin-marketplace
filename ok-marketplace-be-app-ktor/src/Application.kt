package ru.otus.otuskotlin.marketplace.backend.app.ktor

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.producer.Producer
import ru.otus.otuskotlin.marketplace.backend.app.ktor.configs.AuthConfig
import ru.otus.otuskotlin.marketplace.backend.app.ktor.configs.CassandraConfig
import ru.otus.otuskotlin.marketplace.backend.app.ktor.configs.featureAuth
import ru.otus.otuskotlin.marketplace.backend.app.ktor.configs.featureRest
import ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers.*
import ru.otus.otuskotlin.marketplace.backend.app.ktor.exceptions.WrongConfigException
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.DemandService
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.ProposalService
import ru.otus.otuskotlin.marketplace.backend.repository.cassandra.demands.DemandRepositoryCassandra
import ru.otus.otuskotlin.marketplace.backend.repository.cassandra.proposals.ProposalRepositoryCassandra
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
    authOff: Boolean = false,
    kafkaTestConsumer: Consumer<String, String>? = null,
    kafkaTestProducer: Producer<String, String>? = null,
    testDemandRepo: IDemandRepository? = null,
    testProposalRepo: IProposalRepository? = null,
) {
    val authConfig by lazy { AuthConfig(environment, authOff) }
    val cassandraConfig by lazy { CassandraConfig(environment) }

    featureAuth(authConfig)
    featureRest()

    val repoProdName by lazy {
        environment.config.propertyOrNull("marketplace.repository.prod")
            ?.getString()
            ?.trim()
            ?.toLowerCase()
            ?: "inmemory"
    }

    val demandRepoProd = when (repoProdName) {
        "cassandra" -> DemandRepositoryCassandra(
            keyspaceName = cassandraConfig.keyspace,
            hosts = cassandraConfig.hosts,
            port = cassandraConfig.port,
            user = cassandraConfig.user,
            pass = cassandraConfig.pass,
        )
        "inmemory" -> DemandRepoInMemory()
        else -> throw WrongConfigException("Demand repository is not set")
    }
    val proposalRepoProd = when (repoProdName) {
        "cassandra" -> ProposalRepositoryCassandra(
            keyspaceName = cassandraConfig.keyspace,
            hosts = cassandraConfig.hosts,
            port = cassandraConfig.port,
            user = cassandraConfig.user,
            pass = cassandraConfig.pass,
        )
        "inmemory" -> ProposalRepoInMemory()
        else -> throw WrongConfigException("Proposal repository is not set")
    }
    val demandRepoTest = testDemandRepo ?: DemandRepoInMemory(ttl = 2.toDuration(DurationUnit.HOURS))
    val proposalRepoTest = testProposalRepo ?: ProposalRepoInMemory(ttl = 2.toDuration(DurationUnit.HOURS))
    val demandCrud = DemandCrud(
        proposalRepoTest = proposalRepoTest,
        demandRepoTest = demandRepoTest,
        proposalRepoProd = proposalRepoProd,
        demandRepoProd = demandRepoProd,
    )
    val proposalCrud = ProposalCrud(
        proposalRepoTest = proposalRepoTest,
        demandRepoTest = demandRepoTest,
        proposalRepoProd = proposalRepoProd,
        demandRepoProd = demandRepoProd,
    )
    val demandService = DemandService(demandCrud)
    val proposalService = ProposalService(proposalCrud)

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

        demandRouting(demandService, authOff)
        proposalRouting(proposalService, authOff)

    }
}
