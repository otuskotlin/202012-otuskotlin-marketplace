package ru.otus.otuskotlin.marketplace.backend.repository.cassandra

import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.BeforeClass
import org.testcontainers.containers.GenericContainer
import ru.otus.otuskotlin.marketplace.backend.repository.cassandra.demands.DemandRepositoryCassandra
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.*
import java.time.Duration
import kotlin.test.Test
import kotlin.test.assertEquals

class CassandraContainer : GenericContainer<CassandraContainer>("cassandra")

internal class CassandraDemandsTest {

    companion object {
        private val PORT = 9042
        private val keyspace = "test_keyspace"
        private lateinit var container: CassandraContainer
        private lateinit var repo: DemandRepositoryCassandra

        @BeforeClass
        @JvmStatic
        fun tearUp() {
            container = CassandraContainer()
                .withExposedPorts(PORT)
                .withStartupTimeout(Duration.ofSeconds(300L))
                .apply {
                    start()
                }

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

            repo = DemandRepositoryCassandra(
                keyspaceName = keyspace,
                hosts = container.host,
                port = container.getMappedPort(PORT),
                initObjects = listOf(
                    MpDemandModel(
                        id = MpDemandIdModel("test-id1"),
                        title = "test-demand",
                        tagIds = mutableSetOf("id1", "id2"),
                    ),
                    MpDemandModel(
                        id = MpDemandIdModel("test-id2"),
                        title = "test-demand1",
                        tagIds = mutableSetOf("id1", "id2"),
                    ),
                    MpDemandModel(
                        id = MpDemandIdModel("test-id3"),
                        title = "demand-0",
                        tagIds = mutableSetOf("id1", "id2"),
                    ),
                    MpDemandModel(
                        id = MpDemandIdModel("test-id4"),
                        title = "test-demand2",
                        tagIds = mutableSetOf("id1", "id2"),
                    ),
                    MpDemandModel(
                        id = MpDemandIdModel("test-id5"),
                        title = "demand-1",
                        tagIds = mutableSetOf("id1", "id2"),
                    ),
                )
            ).init()
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
                requestDemandId = MpDemandIdModel("test-id2")
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

    @Test
    fun demandUpdateTest() {
        runBlocking {
            val demand = MpDemandModel(
                id = MpDemandIdModel("test-id1"),
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
            val context2 = MpBeContext(requestDemandId = MpDemandIdModel("test-id1"))
            repo.read(context2)
            assertEquals("updated-demand", context2.responseDemand.title)
            assertEquals("about demand", context2.responseDemand.description)
        }
    }

    @Test
    fun demandDeleteTest() {
        runBlocking {
            val context = MpBeContext(
                requestDemandId = MpDemandIdModel("test-id2")
            )
            val model = repo.delete(context)
            assertEquals(model, context.responseDemand)
            assertEquals("test-demand1", model.title)
        }
    }

    @Test
    fun demandOffersTest() {
        runBlocking{
            val context = MpBeContext(
                requestProposal = MpProposalModel(title = "test")
            )
            val result = repo.offers(context)
            assertEquals(result, context.responseDemands)
            assertEquals(3, result.size)
        }
    }
}
