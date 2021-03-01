package ru.otus.otuskotlin.marketplace.mappers.openapi

import ru.otus.otuskotlin.marketplace.common.backend.context.MpBeContext
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.IMpRequest
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
