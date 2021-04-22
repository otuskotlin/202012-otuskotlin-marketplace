package ru.otus.otuskotlin.marketplace.backend.repository.sql

import kotlinx.coroutines.runBlocking
import org.testcontainers.containers.PostgreSQLContainer
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class DemandRepoInSqlTest {

    @Test
    fun createTest() {
        val ctx = MpBeContext(
            requestDemand = MpDemandModel(
                id = demandCreateId,
                avatar = "444",
                title = "rrt",
                description = "ttfd",
                tagIds = mutableSetOf(tag1Id, tag3Id)
            )
        )
        runBlocking {
            val result = repo.create(ctx)

            assertNotEquals(demandCreateId, result.id)
            assertEquals("ttfd", result.description)
            assertEquals(mutableSetOf(tag1Id, tag2Id, tag3Id), result.tagIds)
        }
    }

    @Test
    fun readTest() {
        val ctx = MpBeContext(requestDemandId = demandReadId)
        runBlocking {
            val result = repo.read(ctx)

            assertEquals("ddd", result.description)
            assertEquals(mutableSetOf(tag1Id, tag2Id, tag3Id), result.tagIds)
        }
    }

    @Test
    fun updateTest() {
        val ctx = MpBeContext(requestDemand = MpDemandModel(
            id = demandUpdateId,
            title = "789",
            description = "eer",
            tagIds = mutableSetOf(tag3Id, tag2Id)
        ))
        runBlocking {
            val result = repo.update(ctx)

            assertEquals("789", result.title)
            assertEquals("eer", result.description)
            assertEquals(mutableSetOf(tag2Id, tag3Id), result.tagIds)
        }
    }

    companion object {
        private const val USER = "postgres"
        private const val PASS = "postgres"
        private const val DB = "marketplace"

        private val pg by lazy {
            PostgreSQLContainer<Nothing>("postgres:13").apply {
                withUsername(USER)
                withPassword(PASS)
                withDatabaseName(DB)
                start()
            }
        }

        private val url by lazy { pg.jdbcUrl }

        val demandCreateId = MpDemandIdModel("11111111-1111-1111-1111-111111111110")
        val demandReadId = MpDemandIdModel("11111111-1111-1111-1111-111111111111")
        val demandUpdateId = MpDemandIdModel("11111111-1111-1111-1111-111111111112")
        const val tag1Id = "21111111-1111-1111-1111-111111111111"
        const val tag2Id = "21111111-1111-1111-1111-111111111112"
        const val tag3Id = "21111111-1111-1111-1111-111111111113"
        val repo by lazy {
            DemandRepoInSql(
                url = url,
                user = USER,
                password = PASS,
                printLogs = true,
                initObjects = listOf(
                    MpDemandModel(
                        id = demandReadId,
                        avatar = "123",
                        title = "zyx",
                        description = "ddd",
                        tagIds = mutableSetOf(tag1Id, tag2Id, tag3Id)
                    ),
                    MpDemandModel(
                        id = demandUpdateId,
                        avatar = "123",
                        title = "zyx",
                        description = "ddd",
                        tagIds = mutableSetOf(tag1Id, tag2Id, tag3Id)
                    ),
                )
            )
        }
    }
}
