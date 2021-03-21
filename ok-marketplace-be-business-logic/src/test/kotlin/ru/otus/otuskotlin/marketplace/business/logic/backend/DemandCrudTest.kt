package ru.otus.otuskotlin.marketplace.business.logic.backend

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandFilterModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpStubCase
import runBlockingTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DemandCrudTest {
    @Test
    fun filter() {
        val givenCrud = DemandCrud()
        val givenContext = MpBeContext(
            stubCase = MpStubCase.DEMAND_LIST_SUCCESS,
            demandFilter = MpDemandFilterModel(text = "test")
        )

        runBlockingTest { givenCrud.list(givenContext) }

        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        assertEquals(1, givenContext.responseDemands.size)
        with(givenContext.responseDemands[0]) {
            assertEquals(MpDemandIdModel("test-id"), id)
            assertEquals("test-avatar", avatar)
            assertEquals("test-demand", title)
            assertEquals("test-description", description)
            assertEquals(setOf("1", "2", "3"), tagIds)
        }
    }

    @Test
    fun create() {
        val givenCrud = DemandCrud()
        val givenContext = MpBeContext(
            stubCase = MpStubCase.DEMAND_CREATE_SUCCESS,
            requestDemand = MpDemandModel(
                avatar = "test-avatar",
                title = "test-demand",
                description = "test-description",
                tagIds = mutableSetOf("1", "2", "3")
            )
        )

        runBlockingTest { givenCrud.create(givenContext) }

        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        with(givenContext.responseDemand) {
            assertEquals(MpDemandIdModel("test-id"), id)
            assertEquals("test-avatar", avatar)
            assertEquals("test-demand", title)
            assertEquals("test-description", description)
            assertEquals(setOf("1", "2", "3"), tagIds)
        }
    }

    @Test
    fun read() {
        val givenCrud = DemandCrud()
        val givenContext = MpBeContext(
            stubCase = MpStubCase.DEMAND_READ_SUCCESS,
            requestDemandId = MpDemandIdModel("test-id")
        )

        runBlockingTest { givenCrud.read(givenContext) }

        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        with(givenContext.responseDemand) {
            assertEquals(MpDemandIdModel("test-id"), id)
            assertEquals("test-avatar", avatar)
            assertEquals("test-demand", title)
            assertEquals("test-description", description)
            assertEquals(setOf("1", "2", "3"), tagIds)
        }
    }

    @Test
    fun update() {
        val givenCrud = DemandCrud()
        val givenContext = MpBeContext(
            stubCase = MpStubCase.DEMAND_UPDATE_SUCCESS,
            requestDemand = MpDemandModel(
                id = MpDemandIdModel("test-id"),
                avatar = "test-avatar",
                title = "test-demand",
                description = "test-description",
                tagIds = mutableSetOf("1", "2", "3")
            )
        )

        runBlockingTest { givenCrud.update(givenContext) }

        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        with(givenContext.responseDemand) {
            assertEquals(MpDemandIdModel("test-id"), id)
            assertEquals("test-avatar", avatar)
            assertEquals("test-demand", title)
            assertEquals("test-description", description)
            assertEquals(setOf("1", "2", "3"), tagIds)
        }
    }

    @Test
    fun delete() {
        val givenCrud = DemandCrud()
        val givenContext = MpBeContext(
            stubCase = MpStubCase.DEMAND_DELETE_SUCCESS,
            requestDemandId = MpDemandIdModel("test-id")
        )

        runBlockingTest { givenCrud.delete(givenContext) }

        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        with(givenContext.responseDemand) {
            assertEquals(MpDemandIdModel("test-id"), id)
            assertEquals("test-avatar", avatar)
            assertEquals("test-demand", title)
            assertEquals("test-description", description)
            assertEquals(setOf("1", "2", "3"), tagIds)
        }
    }

    @Test
    fun offers() {
        val givenCrud = DemandCrud()
        val givenContext = MpBeContext(
            stubCase = MpStubCase.DEMAND_OFFERS_SUCCESS,
            requestDemandId = MpDemandIdModel("test-id")
        )

        runBlockingTest { givenCrud.offers(givenContext) }

        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        assertEquals(1, givenContext.responseProposals.size)
        with(givenContext.responseProposals[0]) {
            assertEquals(MpProposalIdModel("test-id"), id)
            assertEquals("test-avatar", avatar)
            assertEquals("test-proposal", title)
            assertEquals("test-description", description)
            assertEquals(setOf("1", "2", "3"), tagIds)
        }
    }
}
