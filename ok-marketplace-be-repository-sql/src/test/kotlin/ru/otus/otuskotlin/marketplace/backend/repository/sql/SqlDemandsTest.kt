package ru.otus.otuskotlin.marketplace.backend.repository.sql

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.testcontainers.containers.PostgreSQLContainer
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.*
import java.time.Duration
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class PostgresContainer : PostgreSQLContainer<PostgresContainer>("postgres")

internal class SqlDemandsTest {

    companion object {
        private const val USER = "postgres"
        private const val PASS = "postgres"
        private const val DB = "marketplace"

        private val testDemandId1 = MpDemandIdModel("11111111-1111-1111-1111-111111111111")
        private val testDemandId2 = MpDemandIdModel("11111111-1111-1111-1111-111111111112")
        private val testDemandId3 = MpDemandIdModel("11111111-1111-1111-1111-111111111113")
        private val testDemandId4 = MpDemandIdModel("11111111-1111-1111-1111-111111111114")
        private val testDemandId5 = MpDemandIdModel("11111111-1111-1111-1111-111111111115")
        private val expectedDemandId = MpDemandIdModel("11111111-1111-1111-1111-111111111120")
        const val tag1Id = "21111111-1111-1111-1111-111111111111"
        const val tag2Id = "21111111-1111-1111-1111-111111111112"
        const val tag3Id = "21111111-1111-1111-1111-111111111113"


        private val container by lazy {
            PostgresContainer().apply {
                withUsername(USER)
                withPassword(PASS)
                withDatabaseName(DB)
                withStartupTimeout(Duration.ofSeconds(300L))
                start()
            }
        }

        private val url by lazy { container.jdbcUrl }

        val unit = MpUnitTypeModel(
            name = "weight",
            synonyms = mutableSetOf("вес"),
            symbol = "kg",
            symbols = mutableSetOf("Kg")
        )

        val param = MpTechParamModel(
            name = "nett",
//                units = mutableSetOf(unit)
        )

        val techDet = MpTechDetModel(
//                param = param,
//                unit = unit,
            value = "100"
        )

        private val repo by lazy {
            DemandRepoSql(
                url = url,
                user = USER,
                password = PASS,
                printLogs = true,
//                keyspaceName = keyspace,
//                hosts = container.host,
//                port = container.getMappedPort(PORT),
//                testing = true,
                initObjects = listOf(
                    MpDemandModel(
                        id = testDemandId1,
                        title = "test-demand",
                        tagIds = mutableSetOf(tag1Id, tag2Id),
                    ),
                    MpDemandModel(
                        id = testDemandId2,
                        title = "test-demand1",
                        tagIds = mutableSetOf(tag1Id, tag2Id),
                    ),
                    MpDemandModel(
                        id = testDemandId3,
                        title = "demand-0",
                        tagIds = mutableSetOf(tag1Id, tag2Id),
                    ),
                    MpDemandModel(
                        id = testDemandId4,
                        title = "test-demand2",
                        tagIds = mutableSetOf(tag1Id, tag2Id),
                    ),
                    MpDemandModel(
                        id = testDemandId5,
                        title = "demand-1",
                        tagIds = mutableSetOf(tag1Id, tag2Id),
                    ),
                )
            )
        }

        @AfterClass
        @JvmStatic
        fun tearDown() {
            container.close()
        }
    }

    @Test
    fun demandReadTest() {
        runBlocking {
            val context = MpBeContext(
                requestDemandId = testDemandId2
            )
            val model = repo.read(context)
            assertEquals(model, context.responseDemand)
            assertEquals("test-demand1", model.title)
        }
    }

    @Test
    fun demandListTest() {
        runBlocking {
            val context = MpBeContext(
                demandFilter = MpDemandFilterModel(
                    text = "test",
                    offset = 1,
                    count = 2,
                )
            )
            val response = repo.list(context)
            assertEquals(response, context.responseDemands)
            assertEquals(2, context.pageCount)
            assertEquals(2, response.size)
        }
    }

    @Test
    fun demandCreateTest() {
        repo
        mockkStatic(UUID::class) {
            every {
                UUID.randomUUID()
            } returns expectedDemandId.asUUID()

            runBlocking {
                val demand = MpDemandModel(
                    title = "created-demand",
                    description = "about demand",
                )
                val context = MpBeContext(
                    requestDemand = demand
                )
                val result = repo.create(context)
                assertEquals(result, context.responseDemand)
                assertEquals("created-demand", result.title)
                assertEquals("about demand", result.description)
                val context2 = MpBeContext(requestDemandId = result.id)
                repo.read(context2)
                assertEquals("created-demand", context2.responseDemand.title)
                assertEquals("about demand", context2.responseDemand.description)
            }
        }
    }

    @Test
    fun demandUpdateTest() {
        runBlocking {
            val demand = MpDemandModel(
                id = testDemandId1,
                title = "updated-demand",
                description = "about demand",
            )
            val context = MpBeContext(
                requestDemand = demand
            )
            val result = repo.update(context)
            assertEquals(result, context.responseDemand)
            assertEquals("updated-demand", result.title)
            assertEquals("about demand", result.description)
            val context2 = MpBeContext(requestDemandId = testDemandId1)
            repo.read(context2)
            assertEquals("updated-demand", context2.responseDemand.title)
            assertEquals("about demand", context2.responseDemand.description)
        }
    }

    @Test
    fun demandDeleteTest() {
        runBlocking {
            val context = MpBeContext(
                requestDemandId = testDemandId2
            )
            val model = repo.delete(context)
            assertEquals(model, context.responseDemand)
            assertEquals("test-demand1", model.title)
        }
    }

    @Test
    fun demandOffersTest() {
        runBlocking {
            val context = MpBeContext(
                requestProposal = MpProposalModel(title = "test")
            )
            val result = repo.offers(context)
            assertEquals(result, context.responseDemands)
            assertEquals(3, result.size)
        }
    }
}
