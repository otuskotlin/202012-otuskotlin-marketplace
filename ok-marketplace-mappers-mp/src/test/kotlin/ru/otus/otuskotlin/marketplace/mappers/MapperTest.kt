package ru.otus.otuskotlin.marketplace.mappers

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpDemandModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpTechDetIdModel
import ru.otus.otuskotlin.marketplace.common.backend.models.MpTechDetModel
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpRequest
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechDetsDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpDemandCreateDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandCreate
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandRead
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MapperTest {

    @Test
    fun requestIdMappingTest() {
        val request: IMpRequest = MpRequestDemandRead(
            demandId = "some-id"
        )
        val context = MpBeContext()

        context.setQuery(request)

        assertEquals("some-id", context.requestDemandId.id)
    }

    @Test
    fun requestCreateMappingTest() {
        val requestDemand: IMpRequest = MpRequestDemandCreate(
            createData = MpDemandCreateDto(
                avatar = "avatar",
                title = "title",
                description = "description",
                tagIds = setOf("tag1", "tag2")
            )
        )

        val context = MpBeContext()

        context.setQuery(requestDemand)

        assertEquals("title", context.requestDemand.title)
        assertEquals(2, context.requestDemand.tagIds.size)

    }
}

private fun MpBeContext.setQuery(request: IMpRequest) =
    when(request){
        is MpRequestDemandRead -> this.setQuery(request)
        is MpRequestDemandCreate -> setQuery(request)
        else -> null
    }

private fun MpBeContext.setQuery(request: MpRequestDemandRead) {
    this.requestDemandId = request.demandId?.let { MpDemandIdModel(it) }?: MpDemandIdModel.NONE
}

private fun MpBeContext.setQuery(request: MpRequestDemandCreate) {
    request.createData?.let { data ->
        this.requestDemand = MpDemandModel(
            avatar = data.avatar?: "",
            title = data.title?: "",
            description = data.description?: "",
            tagIds = data.tagIds?.toMutableSet()?: mutableSetOf(),
            techDets = data.techDets?.map { it.toTechDetModel() }?.toMutableSet()?: mutableSetOf()
        )
    }
}

private fun TechDetsDto.toTechDetModel() =
    MpTechDetModel(
        id = this.id?.let { MpTechDetIdModel(it) }?: MpTechDetIdModel.NONE,
        value = this.value?: "",
        comparableValue = this.comparableValue?: Double.MIN_VALUE,
    )
