package validation

import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.marketplace.backend.app.ktor.jsonConfig
import ru.otus.otuskotlin.marketplace.backend.app.ktor.module
import ru.otus.otuskotlin.marketplace.common.kmp.RestEndpoints
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpProposalListFilterDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpRequestProposalList
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpResponseProposalList
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class ProposalListValidationTest {

    @Test
    fun `non-empty list must success`() {
        withTestApplication({ module()}) {
            handleRequest(HttpMethod.Post, RestEndpoints.proposalList) {
                val body = MpRequestProposalList(
                    requestId = "321",
                    filterData = MpProposalListFilterDto(

                    ),
                    debug = MpRequestProposalList.Debug(
                        mode = MpWorkModeDto.TEST,
                        stubCase = MpRequestProposalList.StubCase.SUCCESS
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

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseProposalList)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("321", res.onRequest)
                assertEquals("test-proposal", res.proposals?.firstOrNull()?.title)
            }
        }
    }

    @Test
    fun `bad json must fail`() {
        withTestApplication({ module()}) {
            handleRequest(HttpMethod.Post, RestEndpoints.proposalList) {
                val bodyString = "{"
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseProposalList)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.BAD_REQUEST, res.status)
                assertTrue {
                    res.errors?.find { it.message?.toLowerCase()?.contains("syntax") == true } != null
                }
            }
        }
    }
}
