package ru.otus.otuskotlin.marketplace.backend.repository.inmemory

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.marketplace.backend.repository.inmemory.demands.DemandRepoInMemory
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpTechDetModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

internal class BaseTest {

    @OptIn(ExperimentalTime::class)
    @Test
    fun createAndGetTest() {
        val repo = DemandRepoInMemory(
            ttl = 5.toDuration(DurationUnit.MINUTES)
        )

        val demand = MpDemandModel(
            title = "demand-1",
            description = "demand-1-description",
            tagIds = mutableSetOf("tag-1"),
            techDets = mutableSetOf(
                MpTechDetModel(
                    value = "tech-det-value"
                )
            )
        )

        val context = MpBeContext(
            requestDemand = demand
        )

        runBlocking {
            val createdDemand = repo.create(context)
            assertEquals("demand-1", createdDemand.title)
            assertEquals("demand-1-description", createdDemand.description)
            assertTrue { createdDemand.tagIds.isNotEmpty() }
            assertTrue { createdDemand.techDets.isNotEmpty() }
            assertEquals("tech-det-value", createdDemand.techDets.first().value)
            context.requestDemandId = createdDemand.id
            val readDemand = repo.read(context)
            assertEquals(createdDemand.id, readDemand.id)
            assertEquals("demand-1", readDemand.title)
            assertEquals("demand-1-description", readDemand.description)
            assertTrue {readDemand.tagIds.isNotEmpty() }
            assertTrue { readDemand.techDets.isNotEmpty() }
            assertEquals("tech-det-value", readDemand.techDets.first().value)

        }
    }
}
