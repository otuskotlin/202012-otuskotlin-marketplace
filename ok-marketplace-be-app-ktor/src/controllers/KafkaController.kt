package ru.otus.otuskotlin.marketplace.backend.app.ktor.controllers

import io.ktor.application.*
import io.ktor.routing.*
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import ru.datana.smart.common.ktor.kafka.KtorKafkaConsumer
import ru.datana.smart.common.ktor.kafka.kafka
import ru.otus.otuskotlin.marketplace.backend.app.ktor.helpers.service
import ru.otus.otuskotlin.marketplace.backend.app.ktor.jsonConfig
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.DemandService
import ru.otus.otuskotlin.marketplace.backend.app.ktor.services.ProposalService
import ru.otus.otuskotlin.marketplace.backend.app.ktor.toModel
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpResponse
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import java.time.Instant
import java.util.*

fun Application.kafkaEndpoints(
    brokers: String,
    kafkaConsumer: Consumer<String, String>?,
    kafkaProducer: Producer<String, String>?,
    demandService: DemandService,
    proposalService: ProposalService,
) {
    val topicIn by lazy { environment.config.property("marketplace.kafka.topicIn").getString() }
    val topicOut by lazy { environment.config.property("marketplace.kafka.topicOut").getString() }
    val producer: Producer<String,String> = kafkaProducer ?: run {
        KafkaProducer(mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to brokers,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
        ))
    }

    install(KtorKafkaConsumer)

    routing {
        kafkaController(
            topicIn,
            topicOut,
            kafkaConsumer = kafkaConsumer,
            kafkaProducer = producer,
            demandService = demandService,
            proposalService = proposalService,
        )
    }
}

fun Routing.kafkaController(
    topicIn: String,
    topicOut: String,
    kafkaConsumer: Consumer<String, String>?,
    kafkaProducer: Producer<String, String>,
    demandService: DemandService,
    proposalService: ProposalService,
) {
    kafka<String, String> {
        keyDeserializer = StringDeserializer::class.java
        valDeserializer = StringDeserializer::class.java
        consumer = kafkaConsumer

        topic(topicIn) {
            println("GOT items")
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
                        println("SEND SUCCESS $it")
                        kafkaProducer.send(
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
                    service(
                        context = ctx,
                        query = null,
                        demandService = demandService,
                        proposalService = proposalService
                    )?.also {
                        println("SEND ERROR $it")
                        kafkaProducer.send(
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
