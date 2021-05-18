import io.ktor.config.*
import io.ktor.server.testing.*
import io.ktor.util.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import ru.datana.smart.common.ktor.kafka.TestConsumer
import ru.datana.smart.common.ktor.kafka.TestProducer
import ru.otus.otuskotlin.marketplace.backend.app.ktor.jsonConfig
import ru.otus.otuskotlin.marketplace.backend.app.ktor.module
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandList
import java.time.Duration
import kotlin.test.Test
import kotlin.test.assertTrue


internal class KafkaTest {
    @OptIn(KtorExperimentalAPI::class)
    @Test
    fun test() {
        val consumer: TestConsumer<String, String> = TestConsumer(duration = Duration.ofMillis(20))
        val producer: TestProducer<String, String> = TestProducer()
        withTestApplication({
            (environment.config as MapApplicationConfig).apply {
                put("marketplace.kafka.topicIn", TOPIC_IN)
                put("marketplace.kafka.topicOut", TOPIC_OT)
                put("marketplace.kafka.brokers", BROKERS)
            }
            module(
                kafkaTestConsumer = consumer,
                kafkaTestProducer = producer,
            )
        }) {
            runBlocking {
                delay(60L)
                val jsonIn = jsonConfig.encodeToString(
                    MpMessage.serializer(),
                    MpRequestDemandList(debug = MpRequestDemandList.Debug(stubCase = MpRequestDemandList.StubCase.SUCCESS))
                )
                consumer.send(TOPIC_IN, "xx1", jsonIn)

                delay(100L)

                val responseObjs = producer.getSent()
                assertTrue("Must return SUCCESS") {
                    val feedBack = responseObjs.first().value()
                    feedBack.contains(Regex("\"status\":\\s*\"SUCCESS\""))
                }
            }
        }
    }

    companion object {
        const val TOPIC_IN = "some-topic-in"
        const val TOPIC_OT = "some-topic-ot"
        const val BROKERS = ""
    }
}

