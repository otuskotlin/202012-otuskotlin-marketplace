package ru.otus.otuskotlin.marketplace.backend.repoDemandsitory.sql

import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.marketplace.backend.repository.sql.SqlAds
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SqlDemandsTest: SqlAds() {

    @Test
    fun demandReadTest() {
        runBlocking {
            val context = MpBeContext(
                requestDemandId = testDemandId2
            )
            val model = repoDemand.read(context)
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
            val response = repoDemand.list(context)
//            println("PORT=${url}")
//            delay(20*1000*60)
            assertEquals(response, context.responseDemands)
            assertEquals(2, context.pageCount)
            assertEquals(1, response.size)
        }
    }

    @Test
    fun demandCreateTest() {
        repoDemand
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
                val result = repoDemand.create(context)
                assertEquals(result, context.responseDemand)
                assertEquals("created-demand", result.title)
                assertEquals("about demand", result.description)
                val context2 = MpBeContext(requestDemandId = result.id)
                repoDemand.read(context2)
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
            val result = repoDemand.update(context)
            assertEquals(result, context.responseDemand)
            assertEquals("updated-demand", result.title)
            assertEquals("about demand", result.description)
            val context2 = MpBeContext(requestDemandId = testDemandId1)
            repoDemand.read(context2)
            assertEquals("updated-demand", context2.responseDemand.title)
            assertEquals("about demand", context2.responseDemand.description)
        }
    }

    @Test
    fun demandDeleteTest() {
        runBlocking {
            val context = MpBeContext(
                requestDemandId = testDemandId5
            )
            val model = repoDemand.delete(context)
            assertEquals(model, context.responseDemand)
            assertEquals("demand-to-delete", model.title)
        }
    }

    @Test
    fun demandOffersTest() {
        runBlocking {
            val context = MpBeContext(
                requestProposal = MpProposalModel(title = "test")
            )
            val result = repoDemand.offers(context)
            assertEquals(result, context.responseDemands)
            assertEquals(3, result.size)
        }
    }
}
