package ru.otus.otuskotlin.marketplace.backend.app.ktor.configs

import io.ktor.application.*
import io.ktor.util.*

data class AuthConfig(
    val secret: String,
    val audience: String,
    val domain: String,
    val realm: String,
) {
    @OptIn(KtorExperimentalAPI::class)
    constructor(environment: ApplicationEnvironment) : this(
        secret = environment.config.propertyOrNull("$PATH.secret")
            ?.getString()
            ?: "marketplace-secret",
        audience = environment.config.propertyOrNull("$PATH.audience")
            ?.getString()
            ?: "marketplace-users",
        domain = environment.config.propertyOrNull("$PATH.domain")
            ?.getString()
            ?: "http://localhost/",
        realm = environment.config.propertyOrNull("$PATH.realm")
            ?.getString()
            ?: "Marketplace",
    )

    companion object {
        const val PATH = "marketplace.auth.jwt"
    }
}
