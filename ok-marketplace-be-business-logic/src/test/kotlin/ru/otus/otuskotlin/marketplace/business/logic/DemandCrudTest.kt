package ru.otus.otuskotlin.marketplace.business.logic

import org.junit.Test
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandFilterModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalFilterModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel
import runBlockingTest
import kotlin.test.assertEquals

class DemandCrudTest {
    @Test
    fun crudGetTest() {
        val givenCrud = DemandCrud()
        val givenContext = MpBeContext(
            requestDemandId = MpDemandIdModel("test-id")
        )
        runBlockingTest {
            givenCrud.get(givenContext)
        }
        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        assertEquals(MpDemandIdModel("test-id"), givenContext.responseDemand.id)
        assertEquals("test-avatar", givenContext.responseDemand.avatar)
        assertEquals("test-demand", givenContext.responseDemand.title)
        assertEquals("test-description", givenContext.responseDemand.description)
        assertEquals(setOf("1", "2", "3"), givenContext.responseDemand.tagIds)
    }

    @Test
    fun crudCreateTest() {
        val givenCrud = DemandCrud()
        val givenContext = MpBeContext(
            requestDemand = MpDemandModel(
                avatar = "test-avatar",
                title = "test-demand",
                description = "test-description",
                tagIds = mutableSetOf("1", "2", "3")
            )
        )
        runBlockingTest {
            givenCrud.create(givenContext)
        }
        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        assertEquals(MpDemandIdModel("test-id"), givenContext.responseDemand.id)
        assertEquals("test-avatar", givenContext.responseDemand.avatar)
        assertEquals("test-demand", givenContext.responseDemand.title)
        assertEquals("test-description", givenContext.responseDemand.description)
        assertEquals(setOf("1", "2", "3"), givenContext.responseDemand.tagIds)
    }

    @Test
    fun crudUpdateTest() {
        val givenCrud = DemandCrud()
        val givenContext = MpBeContext(
            requestDemand = MpDemandModel(
                avatar = "test-avatar",
                title = "test-demand",
                description = "test-description",
                tagIds = mutableSetOf("1", "2", "3")
            )
        )
        runBlockingTest {
            givenCrud.update(givenContext)
        }
        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        assertEquals(MpDemandIdModel("test-id"), givenContext.responseDemand.id)
        assertEquals("test-avatar", givenContext.responseDemand.avatar)
        assertEquals("test-demand", givenContext.responseDemand.title)
        assertEquals("test-description", givenContext.responseDemand.description)
        assertEquals(setOf("1", "2", "3"), givenContext.responseDemand.tagIds)
    }

    @Test
    fun crudDeleteTest() {
        val givenCrud = DemandCrud()
        val givenContext = MpBeContext(
            requestDemandId = MpDemandIdModel("test-id")
        )
        runBlockingTest {
            givenCrud.delete(givenContext)
        }
        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        assertEquals(MpDemandIdModel("test-id"), givenContext.responseDemand.id)
        assertEquals("test-avatar", givenContext.responseDemand.avatar)
        assertEquals("test-demand", givenContext.responseDemand.title)
        assertEquals("test-description", givenContext.responseDemand.description)
        assertEquals(setOf("1", "2", "3"), givenContext.responseDemand.tagIds)
    }

    @Test
    fun crudFilterTest() {
        val givenCrud = DemandCrud()
        val givenContext = MpBeContext(
            demandFilter = MpDemandFilterModel("test-demand")
        )
        runBlockingTest {
            givenCrud.filter(givenContext)
        }
        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        assertEquals(1, givenContext.responseDemands.size)
        assertEquals(MpDemandIdModel("test-id"), givenContext.responseDemands[0].id)
        assertEquals("test-avatar", givenContext.responseDemands[0].avatar)
        assertEquals("test-demand", givenContext.responseDemands[0].title)
        assertEquals("test-description", givenContext.responseDemands[0].description)
        assertEquals(setOf("1", "2", "3"), givenContext.responseDemands[0].tagIds)
    }

    @Test
    fun crudOffersTest() {
        val givenCrud = DemandCrud()
        val givenContext = MpBeContext(
            requestDemandId = MpDemandIdModel("test-id")
        )
        runBlockingTest {
            givenCrud.offers(givenContext)
        }
        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        assertEquals(1, givenContext.responseProposals.size)
        assertEquals(MpProposalIdModel("test-id"), givenContext.responseProposals[0].id)
        assertEquals("test-avatar", givenContext.responseProposals[0].avatar)
        assertEquals("test-proposal", givenContext.responseProposals[0].title)
        assertEquals("test-description", givenContext.responseProposals[0].description)
        assertEquals(setOf("1", "2", "3"), givenContext.responseProposals[0].tagIds)
    }
}