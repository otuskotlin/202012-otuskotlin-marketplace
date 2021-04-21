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
                requestDemandId = MpDemandIdModel("test-id")
            )
            val model = repo.read(context)
            println(model)
            assertEquals(model, context.responseDemand)
        }
    }

    @Test
    fun demandListTest() {
        runBlocking {
            val context = MpBeContext(
                demandFilter = MpDemandFilterModel(
                    text = "test",
                    offset = 2,
                    count = 1,
                )
            )
            val model = repo.list(context)
            println(model)
            println(context.pageCount)
        }
    }
}
