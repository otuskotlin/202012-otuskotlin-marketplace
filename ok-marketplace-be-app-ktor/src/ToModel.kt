package ru.otus.otuskotlin.marketplace.backend.app.ktor

import io.ktor.auth.jwt.*
import kotlinx.serialization.SerializationException
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.marketplace.common.backend.models.MpError
import ru.otus.otuskotlin.marketplace.common.backend.models.MpPrincipalModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpUserGroups
import ru.otus.otuskotlin.marketplace.common.backend.models.MpUserIdModel
import java.security.Principal

private val logger = LoggerFactory.getLogger("Throwable.toModel")

fun Throwable.toModel(): MpError = when (this) {
    is SerializationException -> MpError(message = "Request JSON syntax error: ${this.message}")
    is ClassCastException -> MpError(message = "Wrong data sent to the endpoint: ${this.message}")
    else -> {
        logger.error("Unknown exception", this)
        MpError(message = "Some exception is thrown: ${this.message}")
    }
}

fun JWTPrincipal.toModel() = MpPrincipalModel(
    id = payload.getClaim("id")
        ?.asString()
        ?.let { MpUserIdModel(it) }
        ?: MpUserIdModel.NONE,
    fname = payload.getClaim("fname")?.asString() ?: "",
    mname = payload.getClaim("mname")?.asString() ?: "",
    lname = payload.getClaim("lname")?.asString() ?: "",
    groups = payload
        .getClaim("groups")
        ?.asList(String::class.java)
        ?.mapNotNull {
            try {
                MpUserGroups.valueOf(it)
            } catch (e: Throwable) {
                null
            }
        }
        ?: emptyList()
)
