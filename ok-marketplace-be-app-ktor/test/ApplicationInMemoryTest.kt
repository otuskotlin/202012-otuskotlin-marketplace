import io.ktor.http.*
import io.ktor.server.testing.*
import ru.otus.otuskotlin.marketplace.backend.app.ktor.jsonConfig
import ru.otus.otuskotlin.marketplace.backend.app.ktor.module
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.demands.DemandRepoInMemory
import ru.otus.otuskotlin.marketplace.common.backend.models.*
import ru.otus.otuskotlin.marketplace.common.kmp.RestEndpoints
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpWorkModeDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.ResponseStatusDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

internal class ApplicationInMemoryTest {
    companion object {
        val demand1 = MpDemandModel(
            id = MpDemandIdModel("test-demand-id-1"),
            title = "demand1",
            description = "Некая весьма тяжелая деталь",
            techDets = mutableSetOf(
                MpTechDetModel(
                    param = MpTechParamModel(
                        name = "Weight"
                    ),
                    value = "100",
                    unit = MpUnitTypeModel(name = "kg")
                )
            )
        )
        val demand2 = MpDemandModel(
            id = MpDemandIdModel("test-demand-id-2"),
            title = "demand2",
            description = "Что-то метровой высоты",
            techDets = mutableSetOf(
                MpTechDetModel(
                    param = MpTechParamModel(
                        name = "Height"
                    ),
                    value = "100",
                    unit = MpUnitTypeModel(name = "mm")
                )
            )
        )
        val demand3 = MpDemandModel(
            id = MpDemandIdModel("test-demand-id-3"),
            title = "demand3",
            description = "Очень длинная деталь",
            techDets = mutableSetOf(
                MpTechDetModel(
                    param = MpTechParamModel(
                        name = "Length"
                    ),
                    value = "10",
                    unit = MpUnitTypeModel(name = "m")
                )
            )
        )

        @OptIn(ExperimentalTime::class)
        val repo by lazy {
            DemandRepoInMemory(
                ttl = 15.toDuration(DurationUnit.MINUTES),
                initObjects = listOf(demand1, demand2, demand3)
            )
        }
    }

    @Test
    fun testRead() {
        withTestApplication({module(testDemandRepo = repo)}) {
            handleRequest(HttpMethod.Post, RestEndpoints.demandRead) {
                val body = MpRequestDemandRead(
                    requestId = "12345",
                    demandId = demand1.id.id,
                    debug = MpRequestDemandRead.Debug(mode = MpWorkModeDto.TEST)
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
                assertEquals("12345", res.onRequest)
                assertEquals(demand1.title, res.demand?.title)
                assertEquals(demand1.description, res.demand?.description)
                assertEquals(demand1.techDets.size, res.demand?.techDets?.size)
            }
        }
    }

    @Test
    fun testCreate() {
        withTestApplication({module(testDemandRepo = repo)}) {
            handleRequest(HttpMethod.Post, RestEndpoints.demandCreate) {
                val body = MpRequestDemandCreate(
                    requestId = "12345",
                    createData = MpDemandCreateDto(
                        title = demand2.title,
                        description = demand2.description,
                    ),
                    debug = MpRequestDemandCreate.Debug(mode = MpWorkModeDto.TEST)
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

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseDemandCreate)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("12345", res.onRequest)
                assertEquals(demand2.title, res.demand?.title)
                assertEquals(demand2.description, res.demand?.description)
            }
        }
    }

    @Test
    fun testUpdate() {
        withTestApplication({module(testDemandRepo = repo)}) {
            handleRequest(HttpMethod.Post, RestEndpoints.demandUpdate) {
                val body = MpRequestDemandUpdate(
                    requestId = "12345",
                    updateData = MpDemandUpdateDto(
                      id = demand3.id.id,
                      title = demand3.title,
                      description = demand3.description
                    ),
                    debug = MpRequestDemandUpdate.Debug(mode = MpWorkModeDto.TEST)
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

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseDemandUpdate)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("12345", res.onRequest)
                assertEquals(demand3.id.id, res.demand?.id)
                assertEquals(demand3.title, res.demand?.title)
                assertEquals(demand3.description, res.demand?.description)
                assertEquals(null, res.demand?.techDets)
            }
        }
    }

    @Test
    fun testDelete() {
        withTestApplication({module(testDemandRepo = repo)}) {
            handleRequest(HttpMethod.Post, RestEndpoints.demandDelete) {
                val body = MpRequestDemandDelete(
                    requestId = "12345",
                    demandId = demand2.id.id,
                    debug = MpRequestDemandDelete.Debug(mode = MpWorkModeDto.TEST)
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

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseDemandDelete)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("12345", res.onRequest)
                assertEquals(demand2.id.id, res.demand?.id)
                assertEquals(demand2.title, res.demand?.title)
                assertEquals(demand2.description, res.demand?.description)
                assertEquals(demand2.techDets.size, res.demand?.techDets?.size)
            }
        }
    }

    @Test
    fun testList() {
        withTestApplication({module(testDemandRepo = repo)}) {
            handleRequest(HttpMethod.Post, RestEndpoints.demandList) {
                val body = MpRequestDemandList(
                    requestId = "12345",
                    filterData = MpDemandListFilterDto(
                        text = "дет",
                        offset = 0,
                        count = 10,
                        includeDescription = true,
                    ),
                    debug = MpRequestDemandList.Debug(mode = MpWorkModeDto.TEST)
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

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseDemandList)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("12345", res.onRequest)
                assertEquals(2, res.demands?.size)
                assertEquals(1, res.pageCount)
            }
        }
    }

    @Test
    fun testOffers() {
        withTestApplication({module(testDemandRepo = repo)}) {
            handleRequest(HttpMethod.Post, RestEndpoints.demandOffers) {
                val body = MpRequestDemandOffers(
                    requestId = "12345",
                    debug = MpRequestDemandOffers.Debug(mode = MpWorkModeDto.TEST)
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

                val res = (jsonConfig.decodeFromString(MpMessage.serializer(), jsonString) as? MpResponseDemandList)
                    ?: fail("Incorrect response format")

                assertEquals(ResponseStatusDto.SUCCESS, res.status)
                assertEquals("12345", res.onRequest)
                assertEquals(2, res.demands?.size)
                assertEquals(1, res.pageCount)
            }
        }
    }
}
