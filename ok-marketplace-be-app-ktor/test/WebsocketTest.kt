import io.ktor.http.cio.websocket.*
import io.ktor.server.testing.*
import kotlinx.coroutines.withTimeoutOrNull
import ru.otus.otuskotlin.marketplace.backend.app.ktor.jsonConfig
import ru.otus.otuskotlin.marketplace.backend.app.ktor.module
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandList
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpResponseDemandList
import kotlin.test.Test
import kotlin.test.assertEquals

internal class WebsocketTest {
    @Test
    fun demandListTest() {
        withTestApplication({ module() }) {
            handleWebSocketConversation("/ws") { incoming, outgoing ->
                val query = MpRequestDemandList(
                    requestId = "123",
                    debug = MpRequestDemandList.Debug(
                        stubCase = MpRequestDemandList.StubCase.SUCCESS
                    )
                )
                withTimeoutOrNull(250L) {
                    while (true) {
                        val respJson =(incoming.receive() as Frame.Text).readText()
                        println("GOT INIT RESPONSE: $respJson")
                    }
                }
                val requestJson = jsonConfig.encodeToString(MpMessage.serializer(), query)
                outgoing.send(Frame.Text(requestJson))
                val respJson =(incoming.receive() as Frame.Text).readText()
                println("RESPONSE: $respJson")
                val response = jsonConfig.decodeFromString(MpMessage.serializer(),respJson) as MpResponseDemandList
                assertEquals("123", response.onRequest)

            }
        }
    }
    @Test
    fun demandListErrorTest() {
        withTestApplication({ module() }) {
            handleWebSocketConversation("/ws") { incoming, outgoing ->
                withTimeoutOrNull(250L) {
                    while (true) {
                        val respJson =(incoming.receive() as Frame.Text).readText()
                        println("GOT INIT RESPONSE: $respJson")
                    }
                }
                val requestJson = """{"type":"123"}"""
                outgoing.send(Frame.Text(requestJson))
                val respJson =(incoming.receive() as Frame.Text).readText()
                println("RESPONSE: $respJson")
                val response = jsonConfig.decodeFromString(MpMessage.serializer(),respJson) as MpResponseDemandList
                assertEquals(ResponseStatusDto.BAD_REQUEST, response.status)

            }
        }
    }
}
