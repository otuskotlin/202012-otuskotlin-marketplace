package ru.otus.otuskotlinmarketplace.transport.mappers

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpDemandCreateDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandCreate
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MappersTest {

    @Test
    fun requestMapperTest(){
        val request: MpMessage = MpRequestDemandCreate(
            createData = MpDemandCreateDto(
                title = "demand1",
                tagIds = setOf("123")
            )
        )

        val context = MpBeContext()

        context.setQuery(request)

        assertEquals("demand1", context.requestDemand.title)
    }
}

private fun MpBeContext.setQuery(request: MpMessage) =
    when(request){
        is MpRequestDemandCreate -> setQuery(request)
        else -> null
    }

private fun MpBeContext.setQuery(request: MpRequestDemandCreate) {
    this.requestDemand = request.createData?.toModel()?: MpDemandModel.NONE
}

private fun MpDemandCreateDto?.toModel() = MpDemandModel(
        title = this?.title?:"",
        tagIds = this?.tagIds?.toMutableSet()?: mutableSetOf()
)
