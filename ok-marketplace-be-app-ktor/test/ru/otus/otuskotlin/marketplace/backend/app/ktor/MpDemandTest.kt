package ru.otus.otuskotlin.marketplace.backend.app.ktor

import com.example.jsonConfig
import com.example.module
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class MpDemandTest {

    @Test
    fun `Demand Read Test`() {
        withTestApplication({ module(testing = true)}) {
            handleRequest(HttpMethod.Post, "/demand/read") {
                val body = MpRequestDemandRead(
                    requestId = "321",
                    demandId = "12345",
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
                assertEquals("Demand 12345", res.demand?.title)
            }
        }
    }

    @Test
    fun `Demand Create Test`() {
        withTestApplication({ module(testing = true)}) {
            handleRequest(HttpMethod.Post, "/demand/create") {
                val body = MpRequestDemandCreate(
                    requestId = "321",
                    createData = MpDemandCreateDto(
                        avatar = "icon://example",
                        title = "Some Demand",
                        description = "Demand Description"
                    ),
                )

                val bodyString = jsonConfig.encodeToString(MpMessage.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseDemandCreate)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("321", res.onRequest)
                assertEquals("d-123", res?.demand?.id)
                assertEquals("icon://example", res?.demand?.avatar)
                assertEquals("Some Demand", res?.demand?.title)
                assertEquals("Demand Description", res?.demand?.description)
            }
        }
    }

    @Test
    fun `Demand Update Test`() {
        withTestApplication({ module(testing = true)}) {
            handleRequest(HttpMethod.Post, "/demand/update") {
                val body = MpRequestDemandUpdate(
                    requestId = "321",
                    updateData = MpDemandUpdateDto(
                        id = "d-6543",
                        avatar = "icon://example",
                        title = "Some Demand",
                        description = "Demand Description"
                    ),
                )

                val bodyString = jsonConfig.encodeToString(MpMessage.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseDemandUpdate)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("d-6543", res.demand?.id)
                assertEquals("icon://example", res.demand?.avatar)
                assertEquals("Some Demand", res.demand?.title)
                assertEquals("Demand Description", res.demand?.description)
            }
        }
    }

    @Test
    fun `Demand Delete Test`() {
        withTestApplication({ module(testing = true)}) {
            handleRequest(HttpMethod.Post, "/demand/delete") {
                val body = MpRequestDemandDelete(
                    demandId = "d-6543",
                )

                val bodyString = jsonConfig.encodeToString(MpMessage.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseDemandDelete)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("d-6543", res.demand?.id)
                assertTrue(res.deleted!!)
            }
        }
    }

    @Test
    fun `Demand List Test`() {
        withTestApplication({ module(testing = true)}) {
            handleRequest(HttpMethod.Post, "/demand/list") {
                val body = MpRequestDemandList()

                val bodyString = jsonConfig.encodeToString(MpMessage.serializer(), body)
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
                assertEquals(6, res.demands?.size)
            }
        }
    }

    @Test
    fun `Demand Offers Test`() {
        withTestApplication({ module(testing = true)}) {
            handleRequest(HttpMethod.Post, "/demand/offers") {
                val body = MpRequestDemandOffersList(
                    demandId = "d-6543",
                )

                val bodyString = jsonConfig.encodeToString(MpMessage.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                val jsonString = response.content ?: fail("Null response json")
                println(jsonString)

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseDemandOffersList)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals(4, res.demandProposals?.size)
            }
        }
    }

}
