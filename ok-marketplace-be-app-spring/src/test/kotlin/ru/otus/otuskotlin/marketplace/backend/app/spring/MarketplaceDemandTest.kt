package ru.otus.otuskotlin.marketplace.backend.app.spring

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MarketplaceDemandTest {
    private val client = WebTestClient.bindToServer().baseUrl("http://localhost:8181").build()

    private lateinit var context: ConfigurableApplicationContext

    @BeforeAll
    fun beforeAll() {
        context = app.run(profiles = "test")
    }

    @Test
    fun `Demand List`() {
        val res = client
            .post()
            .uri("/demand/list")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(MpRequestDemandList())
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<MpResponseDemandList>()
            .returnResult()
            .responseBody

        assertEquals(6, res?.demands?.size)
    }

    @Test
    fun `Demand Create`() {
        val res = client
            .post()
            .uri("/demand/create")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(MpRequestDemandCreate(
                createData = MpDemandCreateDto(
                    avatar = "icon://example",
                    title = "Some Demand",
                    description = "Demand Description"
                )
            ))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<MpResponseDemandCreate>()
            .returnResult()
            .responseBody

        assertEquals("d-123", res?.demand?.id)
        assertEquals("icon://example", res?.demand?.avatar)
        assertEquals("Some Demand", res?.demand?.title)
        assertEquals("Demand Description", res?.demand?.description)
    }

    @Test
    fun `Demand Read`() {
        val res = client
            .post()
            .uri("/demand/read")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(MpRequestDemandRead(
                demandId = "d-6547",
            ))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<MpResponseDemandRead>()
            .returnResult()
            .responseBody

        assertEquals("d-6547", res?.demand?.id)
        assertEquals("icon://menu", res?.demand?.avatar)
        assertEquals("Demand d-6547", res?.demand?.title)
        assertEquals("Description of demand d-6547", res?.demand?.description)
    }

    @Test
    fun `Demand Update`() {
        val res = client
            .post()
            .uri("/demand/update")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(MpRequestDemandUpdate(
                updateData = MpDemandUpdateDto(
                    id = "d-6543",
                    avatar = "icon://example",
                    title = "Some Demand",
                    description = "Demand Description"
                )
            ))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<MpResponseDemandDelete>()
            .returnResult()
            .responseBody

        assertEquals("d-6543", res?.demand?.id)
        assertEquals("icon://example", res?.demand?.avatar)
        assertEquals("Some Demand", res?.demand?.title)
        assertEquals("Demand Description", res?.demand?.description)
    }

    @Test
    fun `Demand Delete`() {
        val res = client
            .post()
            .uri("/demand/delete")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(MpRequestDemandDelete(
                demandId = "d-6543",
            ))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<MpResponseDemandDelete>()
            .returnResult()
            .responseBody

        assertEquals("d-6543", res?.demand?.id)
        assertTrue(res?.deleted!!)
    }

    @Test
    fun `Demand Offers`() {
        val res = client
            .post()
            .uri("/demand/offers")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(MpRequestDemandOffersList(
                demandId = "d-6543",
            ))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<MpResponseDemandOffersList>()
            .returnResult()
            .responseBody

        assertEquals(4, res?.demandProposals?.size)
    }

    @AfterAll
    fun afterAll() {
        context.close()
    }

}
