import com.example.jsonConfig
import com.example.module
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandRead
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpResponseDemandRead
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class DemandReadValidationTest {

    @Test
    fun `non-empty demandId must success`() {
        withTestApplication({ module(testing = true)}) {
            handleRequest(HttpMethod.Post, "/demands/get") {
                val body = MpRequestDemandRead(
                    requestId = "321",
                    demandId = "12345",
                    stubCase = MpRequestDemandRead.StubCase.SUCCESS
                )

                val format = jsonConfig

                val bodyString = format.encodeToString(MpMessage.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseDemandRead)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("321", res.onRequest)
                assertEquals("test-demand", res.demand?.title)
            }
        }
    }

    @Test
    fun `empty demandId must fail`() {
        withTestApplication({ module(testing = true)}) {
            handleRequest(HttpMethod.Post, "/demands/get") {
                val body = MpRequestDemandRead(
                    requestId = "321",
                    demandId = "",
                    stubCase = MpRequestDemandRead.StubCase.SUCCESS
                )

                val format = jsonConfig

                val bodyString = format.encodeToString(MpMessage.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseDemandRead)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.BAD_REQUEST, res.status)
                assertEquals("321", res.onRequest)
                assertEquals("test-demand", res.demand?.title)
            }
        }
    }

    @Test
    fun `bad json must fail`() {
        withTestApplication({ module(testing = true)}) {
            handleRequest(HttpMethod.Post, "/demands/get") {
                val bodyString = "{\\}"
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseDemandRead)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.BAD_REQUEST, res.status)
                assertEquals("321", res.onRequest)
                assertEquals("test-demand", res.demand?.title)
            }
        }
    }
}
