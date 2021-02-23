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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MarketplaceKtTest {
    private val client = WebTestClient.bindToServer().baseUrl("http://localhost:8181").build()

    private lateinit var context: ConfigurableApplicationContext

    @BeforeAll
    fun beforeAll() {
        context = app.run(profiles = "test")
    }

    @Test
    fun `Request root endpoint`() {
        client.get().uri("/").header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE).exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<String>().isEqualTo("Hello world!")
    }

    @Test
    fun `Request API endpoint`() {
        client.get().uri("/api").exchange()
            .expectStatus().is2xxSuccessful
            .expectBody<String>().isEqualTo("{\"message\":\"Hello world!\"}")
    }

    @AfterAll
    fun afterAll() {
        context.close()
    }

}
