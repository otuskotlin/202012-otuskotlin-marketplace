package ru.otus.otuskotlin.marketplace.business.logic

import org.junit.Test
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalFilterModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel
import runBlockingTest
import kotlin.test.assertEquals

class ProposalCrudTest {
    @Test
    fun crudGetTest() {
        val givenCrud = ProposalCrud()
        val givenContext = MpBeContext(
            requestProposalId = MpProposalIdModel("test-id")
        )
        runBlockingTest {
            givenCrud.get(givenContext)
        }
        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        assertEquals(MpProposalIdModel("test-id"), givenContext.responseProposal.id)
        assertEquals("test-avatar", givenContext.responseProposal.avatar)
        assertEquals("test-proposal", givenContext.responseProposal.title)
        assertEquals("test-description", givenContext.responseProposal.description)
        assertEquals(setOf("1", "2", "3"), givenContext.responseProposal.tagIds)
    }

    @Test
    fun crudCreateTest() {
        val givenCrud = ProposalCrud()
        val givenContext = MpBeContext(
            requestProposal = MpProposalModel(
                avatar = "test-avatar",
                title = "test-proposal",
                description = "test-description",
                tagIds = mutableSetOf("1", "2", "3")
            )
        )
        runBlockingTest {
            givenCrud.create(givenContext)
        }
        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        assertEquals(MpProposalIdModel("test-id"), givenContext.responseProposal.id)
        assertEquals("test-avatar", givenContext.responseProposal.avatar)
        assertEquals("test-proposal", givenContext.responseProposal.title)
        assertEquals("test-description", givenContext.responseProposal.description)
        assertEquals(setOf("1", "2", "3"), givenContext.responseProposal.tagIds)
    }

    @Test
    fun crudUpdateTest() {
        val givenCrud = ProposalCrud()
        val givenContext = MpBeContext(
            requestProposal = MpProposalModel(
                avatar = "test-avatar",
                title = "test-proposal",
                description = "test-description",
                tagIds = mutableSetOf("1", "2", "3")
            )
        )
        runBlockingTest {
            givenCrud.update(givenContext)
        }
        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        assertEquals(MpProposalIdModel("test-id"), givenContext.responseProposal.id)
        assertEquals("test-avatar", givenContext.responseProposal.avatar)
        assertEquals("test-proposal", givenContext.responseProposal.title)
        assertEquals("test-description", givenContext.responseProposal.description)
        assertEquals(setOf("1", "2", "3"), givenContext.responseProposal.tagIds)
    }

    @Test
    fun crudDeleteTest() {
        val givenCrud = ProposalCrud()
        val givenContext = MpBeContext(
            requestProposalId = MpProposalIdModel("test-id")
        )
        runBlockingTest {
            givenCrud.delete(givenContext)
        }
        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        assertEquals(MpProposalIdModel("test-id"), givenContext.responseProposal.id)
        assertEquals("test-avatar", givenContext.responseProposal.avatar)
        assertEquals("test-proposal", givenContext.responseProposal.title)
        assertEquals("test-description", givenContext.responseProposal.description)
        assertEquals(setOf("1", "2", "3"), givenContext.responseProposal.tagIds)
    }

    @Test
    fun crudFilterTest() {
        val givenCrud = ProposalCrud()
        val givenContext = MpBeContext(
            proposalFilter = MpProposalFilterModel("test-proposal")
        )
        runBlockingTest {
            givenCrud.filter(givenContext)
        }
        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        assertEquals(1, givenContext.responseProposals.size)
        assertEquals(MpProposalIdModel("test-id"), givenContext.responseProposals[0].id)
        assertEquals("test-avatar", givenContext.responseProposals[0].avatar)
        assertEquals("test-proposal", givenContext.responseProposals[0].title)
        assertEquals("test-description", givenContext.responseProposals[0].description)
        assertEquals(setOf("1", "2", "3"), givenContext.responseProposals[0].tagIds)
    }

    @Test
    fun crudOffersTest() {
        val givenCrud = ProposalCrud()
        val givenContext = MpBeContext(
            requestProposalId = MpProposalIdModel("test-id")
        )
        runBlockingTest {
            givenCrud.offers(givenContext)
        }
        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        assertEquals(1, givenContext.responseDemands.size)
        assertEquals(MpDemandIdModel("test-id"), givenContext.responseDemands[0].id)
        assertEquals("test-avatar", givenContext.responseDemands[0].avatar)
        assertEquals("test-demand", givenContext.responseDemands[0].title)
        assertEquals("test-description", givenContext.responseDemands[0].description)
        assertEquals(setOf("1", "2", "3"), givenContext.responseDemands[0].tagIds)
    }
}