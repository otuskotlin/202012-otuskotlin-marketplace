package ru.otus.otuskotlin.marketplace.backend.repoDemandsitory.sql

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.*
import ru.otus.otuskotlin.marketplace.common.backend.repositories.IDemandRepository
import java.util.*

abstract class DemandsRepoBaseTest : StringSpec() {

    abstract val testDemandId1: MpDemandIdModel
    abstract val testDemandId2: MpDemandIdModel
    abstract val testDemandId3: MpDemandIdModel
    abstract val testDemandId4: MpDemandIdModel
    abstract val testDemandId5: MpDemandIdModel
    abstract val expectedDemandId: MpDemandIdModel
    abstract val repoDemand: IDemandRepository

    init {
        "list" {
            val context = MpBeContext(
                demandFilter = MpDemandFilterModel(
                    text = "test",
                    offset = 1,
                    count = 2,
                )
            )
            val response = repoDemand.list(context)
            response shouldBe context.responseDemands
            2 shouldBe context.pageCount
            1 shouldBe response.size
        }

        "read" {
            val context = MpBeContext(
                requestDemandId = testDemandId2
            )
            val model = repoDemand.read(context)
            model shouldBe context.responseDemand
            "test-demand1" shouldBe model.title
        }

        "create" {
            mockkStatic(UUID::class) {expectedDemandId
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
                    result shouldBe context.responseDemand
                    "created-demand" shouldBe result.title
                    "about demand" shouldBe result.description
                    val context2 = MpBeContext(requestDemandId = result.id)
                    repoDemand.read(context2)
                    "created-demand" shouldBe context2.responseDemand.title
                    "about demand" shouldBe context2.responseDemand.description
                }
            }
        }

        "update" {
            val demand = MpDemandModel(
                id = testDemandId1,
                title = "updated-demand",
                description = "about demand",
            )
            val context = MpBeContext(
                requestDemand = demand
            )
            val result = repoDemand.update(context)
            result shouldBe context.responseDemand
            "updated-demand" shouldBe result.title
            "about demand" shouldBe result.description
            val context2 = MpBeContext(requestDemandId = testDemandId1)
            repoDemand.read(context2)
            "updated-demand" shouldBe context2.responseDemand.title
            "about demand" shouldBe context2.responseDemand.description
        }

        "delete" {
            val context = MpBeContext(
                requestDemandId = testDemandId5
            )
            val model = repoDemand.delete(context)
            model shouldBe context.responseDemand
            "demand-to-delete" shouldBe model.title
        }

//        "offers" {
//            val context = MpBeContext(
//                requestProposal = MpProposalModel(title = "test")
//            )
//            val result = repoDemand.offers(context)
//            result shouldBe context.responseDemands
//            3 shouldBe result.size
//        }
    }

    companion object {

    }
}
