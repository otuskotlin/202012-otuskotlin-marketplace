package ru.otus.otuskotlin.marketplace.backend.app.ktor

import com.example.jsonConfig
import com.example.module
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail
import kotlin.test.Test

class MpProposalTest {

    @Test
    fun `Proposal Read Test`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/proposal/read") {
                val body = MpRequestProposalRead(
                    proposalId = "p-6547",
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
                assertEquals("p-6547", res.proposal?.id)
                assertEquals("icon://menu", res.proposal?.avatar)
                assertEquals("Proposal p-6547", res.proposal?.title)
                assertEquals("Description of proposal p-6547", res.proposal?.description)
            }
        }
    }

    @Test
    fun `Proposal Create Test`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/proposal/create") {
                val body = MpRequestProposalCreate(
                    createData = MpProposalCreateDto(
                        avatar = "icon://example",
                        title = "Some Proposal",
                        description = "Proposal Description"
                    )
                )

                val bodyString = jsonConfig.encodeToString(MpMessage.serializer(), body)
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
                assertEquals("p-123", res.proposal?.id)
                assertEquals("icon://example", res.proposal?.avatar)
                assertEquals("Some Proposal", res.proposal?.title)
                assertEquals("Proposal Description", res.proposal?.description)
            }
        }
    }

    @Test
    fun `Proposal Update Test`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/proposal/update") {
                val body = MpRequestProposalUpdate(
                    updateData = MpProposalUpdateDto(
                        id = "p-6543",
                        avatar = "icon://example",
                        title = "Some Proposal",
                        description = "Proposal Description"
                    )
                )

                val bodyString = jsonConfig.encodeToString(MpMessage.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseProposalUpdate)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("p-6543", res.proposal?.id)
                assertEquals("icon://example", res.proposal?.avatar)
                assertEquals("Some Proposal", res.proposal?.title)
                assertEquals("Proposal Description", res.proposal?.description)
            }
        }
    }

    @Test
    fun `Proposal Delete Test`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/proposal/delete") {
                val body = MpRequestProposalDelete(
                    proposalId = "p-6543",
                )

                val bodyString = jsonConfig.encodeToString(MpMessage.serializer(), body)
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
                assertEquals("p-6543", res.proposal?.id)
                assertTrue(res.deleted!!)
            }
        }
    }

    @Test
    fun `Proposal List Test`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/proposal/list") {
                val body = MpRequestProposalList()

                val bodyString = jsonConfig.encodeToString(MpMessage.serializer(), body)
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
                assertEquals(6, res.proposals?.size)
            }
        }
    }

    @Test
    fun `Proposal Offers Test`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/proposal/offers") {
                val body = MpRequestProposalOffersList(
                    proposalId = "p-6543",
                )

                val bodyString = jsonConfig.encodeToString(MpMessage.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res =
                    (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseProposalOffersList)
                        ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals(4, res.proposalDemands?.size)
            }
        }
    }
}

