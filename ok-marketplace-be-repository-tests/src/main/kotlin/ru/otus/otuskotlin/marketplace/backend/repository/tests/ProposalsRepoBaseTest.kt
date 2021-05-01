package ru.otus.otuskotlin.marketplace.backend.repoProposalsitory.sql

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.*
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IProposalRepository
import java.util.*

abstract class ProposalsRepoBaseTest : StringSpec() {

    abstract val testProposalId1: MpProposalIdModel
    abstract val testProposalId2: MpProposalIdModel
    abstract val testProposalId3: MpProposalIdModel
    abstract val testProposalId4: MpProposalIdModel
    abstract val testProposalId5: MpProposalIdModel
    abstract val expectedProposalId: MpProposalIdModel
    abstract val repoProposal: IProposalRepository

    init {
        "list" {
            val context = MpBeContext(
                proposalFilter = MpProposalFilterModel(
                    text = "test",
                    offset = 1,
                    count = 2,
                )
            )
            val response = repoProposal.list(context)
            response shouldBe context.responseProposals
            2 shouldBe context.pageCount
            1 shouldBe response.size
        }

        "read" {
            val context = MpBeContext(
                requestProposalId = testProposalId2
            )
            val model = repoProposal.read(context)
            model shouldBe context.responseProposal
            "test-proposal1" shouldBe model.title
        }

        "create" {
            mockkStatic(UUID::class) {expectedProposalId
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
                    result shouldBe context.responseProposal
                    "created-proposal" shouldBe result.title
                    "about proposal" shouldBe result.description
                    val context2 = MpBeContext(requestProposalId = result.id)
                    repoProposal.read(context2)
                    "created-proposal" shouldBe context2.responseProposal.title
                    "about proposal" shouldBe context2.responseProposal.description
                }
            }
        }

        "update" {
            val proposal = MpProposalModel(
                id = testProposalId1,
                title = "updated-proposal",
                description = "about proposal",
            )
            val context = MpBeContext(
                requestProposal = proposal
            )
            val result = repoProposal.update(context)
            result shouldBe context.responseProposal
            "updated-proposal" shouldBe result.title
            "about proposal" shouldBe result.description
            val context2 = MpBeContext(requestProposalId = testProposalId1)
            repoProposal.read(context2)
            "updated-proposal" shouldBe context2.responseProposal.title
            "about proposal" shouldBe context2.responseProposal.description
        }

        "delete" {
            val context = MpBeContext(
                requestProposalId = testProposalId5
            )
            val model = repoProposal.delete(context)
            model shouldBe context.responseProposal
            "proposal-to-delete" shouldBe model.title
        }

//        "offers" {
//            val context = MpBeContext(
//                requestProposal = MpProposalModel(title = "test")
//            )
//            val result = repoProposal.offers(context)
//            result shouldBe context.responseProposals
//            3 shouldBe result.size
//        }
    }

}
