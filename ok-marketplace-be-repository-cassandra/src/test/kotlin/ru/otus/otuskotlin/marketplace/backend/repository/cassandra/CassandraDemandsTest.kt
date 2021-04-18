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
                        id = MpDemandIdModel("test-id"),
                        title = "test-demand",
                        tagIds = mutableSetOf("id1", "id2"),
                        techDets = mutableSetOf(
//                            techDet.copy(value = "200"),
                            techDet
//                            techDet.copy(comparableValue = 2.5)
                            )
                    )
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
}
