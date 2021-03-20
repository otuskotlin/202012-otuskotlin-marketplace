package ru.otus.otuskotlin.marketplace.business.logic.backend

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.marketplace.business.logic.backend.pipelines.DemandRead
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContextStatus
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class DemandReadValidation {

    @Test
    fun `demandId success non-empty`() {
        val ctx = MpBeContext(
            requestDemandId = MpDemandIdModel("123")
        )

        runBlocking {
            DemandRead.execute(ctx)
            assertEquals(MpBeContextStatus.SUCCESS, ctx.status)
            assertEquals(0, ctx.errors.size)
        }
    }

    @Test
    fun `demandId fails empty`() {
        val ctx = MpBeContext(
            requestDemandId = MpDemandIdModel("")
        )

        runBlocking {
            DemandRead.execute(ctx)
            assertEquals(MpBeContextStatus.ERROR, ctx.status)
            assertTrue { ctx.errors.first().message.contains("empty") }
        }
    }
}
