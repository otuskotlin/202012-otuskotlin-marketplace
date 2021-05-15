import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.config.*
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.marketplace.backend.app.ktor.jsonConfig
import ru.otus.otuskotlin.marketplace.backend.app.ktor.module
import ru.otus.otuskotlin.marketplace.common.kmp.RestEndpoints
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandRead
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpResponseDemandRead
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class AuthJwtTest {
    companion object {
        const val SECRET = "marketplace-secret"
        const val AUDIENCE = "test-mp-audience"
        const val REALM = "test-mp-realm"
        const val DOMAIN = "http://localhost/"
        const val USER_ID = "test-user-id"
        const val USER_FNAME = "Ivan"
        const val USER_MNAME = "Ivanovich"
        const val USER_LNAME = "Ivanov"
        val TOKEN = JWT.create()
            .withSubject("Authentication")
            .withIssuer(DOMAIN)
            .withAudience(AUDIENCE)
            .withClaim("id", USER_ID)
            .withClaim("fname", USER_FNAME)
            .withClaim("mname", USER_MNAME)
            .withClaim("lname", USER_LNAME)
            .withArrayClaim("groups", arrayOf("USER", "ADMIN_MP", "MODERATOR_MP"))
//            .withExpiresAt(getExpiration())
            .sign(Algorithm.HMAC256(SECRET))
            .toString()
    }

    @Test
    fun jwtTest() {
        println(TOKEN)
        withTestApplication({
            (environment.config as MapApplicationConfig).apply {
                put("marketplace.auth.jwt.secret", SECRET)
                put("marketplace.auth.jwt.audience", AUDIENCE)
                put("marketplace.auth.jwt.domain", DOMAIN)
                put("marketplace.auth.jwt.realm", REALM)
//                put("marketplace.kafka.topicIn", TOPIC_IN)
//                put("marketplace.kafka.topicOut", TOPIC_OT)
//                put("marketplace.kafka.brokers", BROKERS)
            }
            module(testing = true)
        }) {
            handleRequest(HttpMethod.Post, RestEndpoints.demandRead) {
                val body = MpRequestDemandRead(
                    requestId = "321",
                    demandId = "12345",
                    debug = MpRequestDemandRead.Debug(stubCase = MpRequestDemandRead.StubCase.SUCCESS)
                )

                val format = jsonConfig

                val bodyString = format.encodeToString(MpMessage.serializer(), body)
                setBody(bodyString)
                addHeader("Content-Type", "application/json")
                addHeader("Authorization", "Bearer $TOKEN")
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
}
