package ru.otus.otuskotlin.marketplace.mappers

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpRequest
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandRead
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MapperTest {

    @Test
    fun requestIdMappingTest(){
        val request: IMpRequest = MpRequestDemandRead(
            demandId = "some-id"
        )
        val context = MpBeContext()

        context.setQuery(request)

        assertEquals("some-id", context.requestDemandId.id)
    }
}

private fun MpBeContext.setQuery(request: IMpRequest) =
    when(request){
        is MpRequestDemandRead -> this.setQuery(request)
        else -> null
    }

private fun MpBeContext.setQuery(request: MpRequestDemandRead) {
    this.requestDemandId = request.demandId?.let { MpDemandIdModel(it) }?: MpDemandIdModel.NONE
}

