package ru.otus.otuskotlin.marketplace.business.logic.backend

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalFilterModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpStubCase
import runBlockingTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ProposalCrudTest {
    @Test
    fun filter() {
        val givenCrud = ProposalCrud()
        val givenContext = MpBeContext(
            stubCase = MpStubCase.PROPOSAL_FILTER_SUCCESS,
            proposalFilter = MpProposalFilterModel(text = "test")
        )

        runBlockingTest { givenCrud.filter(givenContext) }

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

    @Test
    fun create() {
        val givenCrud = ProposalCrud()
        val givenContext = MpBeContext(
            stubCase = MpStubCase.PROPOSAL_CREATE_SUCCESS,
            requestProposal = MpProposalModel(
                avatar = "test-avatar",
                title = "test-proposal",
                description = "test-description",
                tagIds = mutableSetOf("1", "2", "3")
            )
        )

        runBlockingTest { givenCrud.create(givenContext) }

        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        with(givenContext.responseProposal) {
            assertEquals(MpProposalIdModel("test-id"), id)
            assertEquals("test-avatar", avatar)
            assertEquals("test-proposal", title)
            assertEquals("test-description", description)
            assertEquals(setOf("1", "2", "3"), tagIds)
        }
    }

    @Test
    fun read() {
        val givenCrud = ProposalCrud()
        val givenContext = MpBeContext(
            stubCase = MpStubCase.PROPOSAL_READ_SUCCESS,
            requestProposalId = MpProposalIdModel("test-id")
        )

        runBlockingTest { givenCrud.read(givenContext) }

        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        with(givenContext.responseProposal) {
            assertEquals(MpProposalIdModel("test-id"), id)
            assertEquals("test-avatar", avatar)
            assertEquals("test-proposal", title)
            assertEquals("test-description", description)
            assertEquals(setOf("1", "2", "3"), tagIds)
        }
    }

    @Test
    fun update() {
        val givenCrud = ProposalCrud()
        val givenContext = MpBeContext(
            stubCase = MpStubCase.PROPOSAL_UPDATE_SUCCESS,
            requestProposal = MpProposalModel(
                id = MpProposalIdModel("test-id"),
                avatar = "test-avatar",
                title = "test-proposal",
                description = "test-description",
                tagIds = mutableSetOf("1", "2", "3")
            )
        )

        runBlockingTest { givenCrud.update(givenContext) }

        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        with(givenContext.responseProposal) {
            assertEquals(MpProposalIdModel("test-id"), id)
            assertEquals("test-avatar", avatar)
            assertEquals("test-proposal", title)
            assertEquals("test-description", description)
            assertEquals(setOf("1", "2", "3"), tagIds)
        }
    }

    @Test
    fun delete() {
        val givenCrud = ProposalCrud()
        val givenContext = MpBeContext(
            stubCase = MpStubCase.PROPOSAL_DELETE_SUCCESS,
            requestProposalId = MpProposalIdModel("test-id")
        )

        runBlockingTest { givenCrud.delete(givenContext) }

        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)

        assertEquals(MpBeContextStatus.SUCCESS, givenContext.status)
        with(givenContext.responseProposal) {
            assertEquals(MpProposalIdModel("test-id"), id)
            assertEquals("test-avatar", avatar)
            assertEquals("test-proposal", title)
            assertEquals("test-description", description)
            assertEquals(setOf("1", "2", "3"), tagIds)
        }
    }

    @Test
    fun offers() {
        val givenCrud = ProposalCrud()
        val givenContext = MpBeContext(
            stubCase = MpStubCase.PROPOSAL_OFFERS_SUCCESS,
            requestProposalId = MpProposalIdModel("test-id")
        )

        runBlockingTest { givenCrud.offers(givenContext) }

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
}