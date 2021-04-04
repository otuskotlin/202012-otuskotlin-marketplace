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
import ru.otus.otuskotlin.marketplace.transport.kmp.models.proposals.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MarketplaceProposalTest {
    private val client = WebTestClient.bindToServer().baseUrl("http://localhost:8181").build()

    private lateinit var context: ConfigurableApplicationContext

    @BeforeAll
    fun beforeAll() {
        context = app.run(profiles = "test")
    }

    @Test
    fun `Proposal List`() {
        val res = client
            .post()
            .uri("/proposal/list")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(MpRequestProposalList(
                debug = MpRequestProposalList.Debug(
                    stubCase = MpRequestProposalList.StubCase.SUCCESS
                )
            ))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<MpResponseProposalList>()
            .returnResult()
            .responseBody

        assertEquals(1, res?.proposals?.size)
    }

    @Test
    fun `Proposal Create`() {
        val res = client
            .post()
            .uri("/proposal/create")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(MpRequestProposalCreate(
                createData = MpProposalCreateDto(
                    avatar = "icon://example",
                    title = "Some Proposal",
                    description = "Proposal Description"
                ),
                debug = MpRequestProposalCreate.Debug(
                    stubCase = MpRequestProposalCreate.StubCase.SUCCESS
                ),
            ))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<MpResponseProposalCreate>()
            .returnResult()
            .responseBody

        assertEquals("test-id", res?.proposal?.id)
        assertEquals("icon://example", res?.proposal?.avatar)
        assertEquals("Some Proposal", res?.proposal?.title)
        assertEquals("Proposal Description", res?.proposal?.description)
    }

    @Test
    fun `Proposal Read`() {
        val res = client
            .post()
            .uri("/proposal/read")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(MpRequestProposalRead(
                proposalId = "p-6547",
                debug = MpRequestProposalRead.Debug(
                    stubCase = MpRequestProposalRead.StubCase.SUCCESS
                )
            ))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<MpResponseProposalRead>()
            .returnResult()
            .responseBody

        assertEquals("p-6547", res?.proposal?.id)
        assertEquals("icon://menu", res?.proposal?.avatar)
        assertEquals("Proposal p-6547", res?.proposal?.title)
        assertEquals("Description of proposal p-6547", res?.proposal?.description)
    }

    @Test
    fun `Proposal Update`() {
        val res = client
            .post()
            .uri("/proposal/update")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(MpRequestProposalUpdate(
                updateData = MpProposalUpdateDto(
                    id = "p-6543",
                    avatar = "icon://example",
                    title = "Some Proposal",
                    description = "Proposal Description"
                ),
                debug = MpRequestProposalUpdate.Debug(
                    stubCase = MpRequestProposalUpdate.StubCase.SUCCESS
                ),
            ))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<MpResponseProposalDelete>()
            .returnResult()
            .responseBody

        assertEquals("p-6543", res?.proposal?.id)
        assertEquals("icon://example", res?.proposal?.avatar)
        assertEquals("Some Proposal", res?.proposal?.title)
        assertEquals("Proposal Description", res?.proposal?.description)
    }

    @Test
    fun `Proposal Delete`() {
        val res = client
            .post()
            .uri("/proposal/delete")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(MpRequestProposalDelete(
                proposalId = "p-6543",
                debug = MpRequestProposalDelete.Debug(
                    stubCase = MpRequestProposalDelete.StubCase.SUCCESS
                )
            ))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<MpResponseProposalDelete>()
            .returnResult()
            .responseBody

        assertEquals("p-6543", res?.proposal?.id)
    }

    @Test
    fun `Proposal Offers`() {
        val res = client
            .post()
            .uri("/proposal/offers")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(MpRequestProposalOffers(
                proposalId = "p-6543",
                debug = MpRequestProposalOffers.Debug(
                    stubCase = MpRequestProposalOffers.StubCase.SUCCESS
                ),
            ))
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<MpResponseProposalOffers>()
            .returnResult()
            .responseBody

        assertEquals(1, res?.proposalDemands?.size)
    }

    @AfterAll
    fun afterAll() {
        context.close()
    }

}
