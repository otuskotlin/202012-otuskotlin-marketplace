package validation

import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.marketplace.backend.app.ktor.jsonConfig
import ru.otus.otuskotlin.marketplace.backend.app.ktor.module
import ru.otus.otuskotlin.marketplace.common.kmp.RestEndpoints
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpProposalCreateDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpRequestProposalCreate
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.MpResponseProposalCreate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class ProposalCreateValidationTest {

    @Test
    fun `non-empty create must success`() {
        withTestApplication({ module()}) {
            handleRequest(HttpMethod.Post, RestEndpoints.proposalCreate) {
                val body = MpRequestProposalCreate(
                    requestId = "321",
                    createData = MpProposalCreateDto(
                        avatar = "avatar",
                        title = "Proposal Title",
                        description = "proposal desc",
                        tagIds = setOf("123","223"),
                        techDets = setOf(
                            TechDetsDto(
                                id = "x123",
                                param = TechParamDto(
                                    id = "y-345",
                                    name = "par-1",
                                    description = "par des",
                                    units = setOf(
                                        UnitTypeDto(
                                            id = "u-232",
                                            name = "uni",
                                            description = "Uni",
                                            symbol = "un",
                                        )
                                    )
                                )
                            )
                        )
                    ),
                    debug = MpRequestProposalCreate.Debug(
                        mode = MpWorkModeDto.TEST,
                        stubCase = MpRequestProposalCreate.StubCase.SUCCESS
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

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseProposalCreate)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("321", res.onRequest)
                assertEquals("Proposal Title", res.proposal?.title)
            }
        }
    }

    @Test
    fun `empty title or description must fail`() {
        withTestApplication({ module()}) {
            handleRequest(HttpMethod.Post, RestEndpoints.proposalCreate) {
                val body = MpRequestProposalCreate(
                    requestId = "321",
                    createData = MpProposalCreateDto(

                    )
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

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseProposalCreate)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.BAD_REQUEST, res.status)
                assertEquals("321", res.onRequest)
                assertTrue {
                    res.errors?.firstOrNull {
                        it.message?.contains("title") == true
                                && it.message?.contains("empty") == true
                    } != null
                }
                assertTrue {
                    res.errors?.firstOrNull {
                        it.message?.contains("description") == true
                                && it.message?.contains("empty") == true
                    } != null
                }
            }
        }
    }

    @Test
    fun `bad json must fail`() {
        withTestApplication({ module()}) {
            handleRequest(HttpMethod.Post, RestEndpoints.proposalCreate) {
                val bodyString = "{"
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseProposalCreate)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.BAD_REQUEST, res.status)
                assertTrue {
                    res.errors?.find { it.message?.toLowerCase()?.contains("syntax") == true } != null
                }
            }
        }
    }
}
