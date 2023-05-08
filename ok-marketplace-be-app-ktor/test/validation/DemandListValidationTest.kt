package validation

import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.marketplace.backend.app.ktor.jsonConfig
import ru.otus.otuskotlin.marketplace.backend.app.ktor.module
import ru.otus.otuskotlin.marketplace.common.kmp.RestEndpoints
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class DemandListValidationTest {

    @Test
    fun `non-empty list must success`() {
        withTestApplication({ module()}) {
            handleRequest(HttpMethod.Post, RestEndpoints.demandList) {
                val body = MpRequestDemandList(
                    requestId = "321",
                    filterData = MpDemandListFilterDto(

                    ),
                    debug = MpRequestDemandList.Debug(
                        mode = MpWorkModeDto.TEST,
                        stubCase = MpRequestDemandList.StubCase.SUCCESS
                    )
                )

                val bodyString = jsonConfig.encodeToString(MpMessage.serializer(), body)
                println("REQUEST JSON: $bodyString")
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseDemandList)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("321", res.onRequest)
                assertEquals("test-demand", res.demands?.firstOrNull()?.title)
            }
        }
    }

    @Test
    fun `bad json must fail`() {
        withTestApplication({ module()}) {
            handleRequest(HttpMethod.Post, RestEndpoints.demandList) {
                val bodyString = "{"
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseDemandList)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.BAD_REQUEST, res.status)
                assertTrue {
                    res.errors?.find { it.message?.toLowerCase()?.contains("syntax") == true } != null
                }
            }
        }
    }
}
