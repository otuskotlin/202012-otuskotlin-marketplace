import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.marketplace.backend.app.ktor.jsonConfig
import ru.otus.otuskotlin.marketplace.backend.app.ktor.module
import ru.otus.otuskotlin.marketplace.common.kmp.RestEndpoints
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class ProposalDeleteValidationTest {

    @Test
    fun `non-empty delete must success`() {
        withTestApplication({ module(testing = true)}) {
            handleRequest(HttpMethod.Post, RestEndpoints.proposalDelete) {
                val body = MpRequestProposalDelete(
                    requestId = "321",
                    proposalId = "p-333",
                    debug = MpRequestProposalDelete.Debug(
                        mode = MpWorkModeDto.TEST,
                        stubCase = MpRequestProposalDelete.StubCase.SUCCESS
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

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseProposalDelete)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("321", res.onRequest)
                assertEquals("p-333", res.proposal?.id)
                assertEquals("test-proposal", res.proposal?.title)
            }
        }
    }

    @Test
    fun `empty proposal id must fail`() {
        withTestApplication({ module(testing = true)}) {
            handleRequest(HttpMethod.Post, RestEndpoints.proposalDelete) {
                val body = MpRequestProposalDelete(
                    requestId = "321",
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

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseProposalDelete)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.BAD_REQUEST, res.status)
                assertEquals("321", res.onRequest)
                assertTrue("errors: ${res.errors}") {
                    res.errors?.firstOrNull {
                        it.message?.toLowerCase()?.contains("id") == true
                                && it.message?.toLowerCase()?.contains("empty") == true
                    } != null
                }
            }
        }
    }

    @Test
    fun `bad json must fail`() {
        withTestApplication({ module(testing = true)}) {
            handleRequest(HttpMethod.Post, RestEndpoints.proposalDelete) {
                val bodyString = "{"
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseProposalDelete)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.BAD_REQUEST, res.status)
                assertTrue {
                    res.errors?.find { it.message?.toLowerCase()?.contains("syntax") == true } != null
                }
            }
        }
    }
}
