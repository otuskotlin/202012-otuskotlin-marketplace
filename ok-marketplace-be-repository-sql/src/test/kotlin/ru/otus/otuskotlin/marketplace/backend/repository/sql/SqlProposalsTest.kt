package ru.otus.otuskotlin.marketplace.backend.repoProposalsitory.sql

import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.marketplace.backend.repository.sql.SqlAds
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalFilterModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpProposalModel
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SqlProposalsTest: SqlAds() {

    @Test
    fun proposalReadTest() {
        println("TESTPROP: ${testProposalId2.asString()}")
        runBlocking {
            val context = MpBeContext(
                requestProposalId = testProposalId2
            )
            val model = repoProposal.read(context)
            assertEquals(model, context.responseProposal)
            assertEquals("test-proposal1", model.title)
        }
    }

    @Test
    fun proposalListTest() {
        runBlocking {
            val context = MpBeContext(
                proposalFilter = MpProposalFilterModel(
                    text = "test",
                    offset = 1,
                    count = 2,
                )
            )
            val response = repoProposal.list(context)
            assertEquals(response, context.responseProposals)
            assertEquals(2, context.pageCount)
            assertEquals(1, response.size)
        }
    }

    @Test
    fun proposalCreateTest() {
        repoProposal
        mockkStatic(UUID::class) {
            every {
                UUID.randomUUID()
            } returns expectedProposalId.asUUID()

            runBlocking {
                val proposal = MpProposalModel(
                    title = "created-proposal",
                    description = "about proposal",
                )
                val context = MpBeContext(
                    requestProposal = proposal
                )
                val result = repoProposal.create(context)
                assertEquals(result, context.responseProposal)
                assertEquals("created-proposal", result.title)
                assertEquals("about proposal", result.description)
                val context2 = MpBeContext(requestProposalId = result.id)
                repoProposal.read(context2)
                assertEquals("created-proposal", context2.responseProposal.title)
                assertEquals("about proposal", context2.responseProposal.description)
            }
        }
    }

    @Test
    fun proposalUpdateTest() {
        runBlocking {
            val proposal = MpProposalModel(
                id = testProposalId1,
                title = "updated-proposal",
                description = "about proposal",
            )
            val context = MpBeContext(
                requestProposal = proposal
            )
            val result = repoProposal.update(context)
            assertEquals(result, context.responseProposal)
            assertEquals("updated-proposal", result.title)
            assertEquals("about proposal", result.description)
            val context2 = MpBeContext(requestProposalId = testProposalId1)
            repoProposal.read(context2)
            assertEquals("updated-proposal", context2.responseProposal.title)
            assertEquals("about proposal", context2.responseProposal.description)
        }
    }

    @Test
    fun proposalDeleteTest() {
        runBlocking {
            val context = MpBeContext(
                requestProposalId = testProposalId5
            )
            val model = repoProposal.delete(context)
            assertEquals(model, context.responseProposal)
            assertEquals("proposal-to-delete", model.title)
        }
    }

    @Test
    fun proposalOffersTest() {
        runBlocking {
            val context = MpBeContext(
                requestProposal = MpProposalModel(title = "test")
            )
            val result = repoProposal.offers(context)
            assertEquals(result, context.responseProposals)
            assertEquals(3, result.size)
        }
    }
}
