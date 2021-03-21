import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.marketplace.backend.app.ktor.jsonConfig
import ru.otus.otuskotlin.marketplace.backend.app.ktor.module
import ru.otus.otuskotlin.marketplace.common.kmp.RestEndpoints
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpRequestProposalRead
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpResponseProposalRead
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class ProposalReadValidationTest {

    @Test
    fun `non-empty proposalId must success`() {
        withTestApplication({ module(testing = true)}) {
            handleRequest(HttpMethod.Post, RestEndpoints.proposalRead) {
                val body = MpRequestProposalRead(
                    requestId = "321",
                    proposalId = "12345",
                    debug = MpRequestProposalRead.Debug(stubCase = MpRequestProposalRead.StubCase.SUCCESS)
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

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseProposalRead)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("321", res.onRequest)
                assertEquals("test-proposal", res.proposal?.title)
            }
        }
    }

    @Test
    fun `empty proposalId must fail`() {
        withTestApplication({ module(testing = true)}) {
            handleRequest(HttpMethod.Post, RestEndpoints.proposalRead) {
                val body = MpRequestProposalRead(
                    requestId = "321",
                    proposalId = "",
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

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseProposalRead)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.BAD_REQUEST, res.status)
                assertEquals("321", res.onRequest)
            }
        }
    }

    @Test
    fun `bad json must fail`() {
        withTestApplication({ module(testing = true)}) {
            handleRequest(HttpMethod.Post, RestEndpoints.proposalRead) {
                val bodyString = "{"
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseProposalRead)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.BAD_REQUEST, res.status)
                assertTrue {
                    res.errors?.find { it.message?.toLowerCase()?.contains("syntax") == true } != null
                }
            }
        }
    }
}
