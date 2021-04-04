package ru.otus.otuskotlin.marketplace.transport.kmp.models

import kotlinx.serialization.json.Json
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpWorkModeDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechDetsDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpDemandCreateDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandCreate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DebugSerializationTest {

    @Test
    fun debugSerialibzation(){
        val json = Json {
            prettyPrint = true
        }
        val dto = MpRequestDemandCreate(
            requestId = "create-id",
            startTime = "2021-02-13T12:00:00",
            createData = MpDemandCreateDto(
                title = "demand-1",
                description = "some description",
                techDets = setOf(TechDetsDto(
                    id = "tech-det-id"
                ))
            ),
            debug = MpRequestDemandCreate.Debug(
                mode = MpWorkModeDto.TEST,
                stubCase = MpRequestDemandCreate.StubCase.SUCCESS
            )
        )

        val serializedString = json.encodeToString(MpRequestDemandCreate.serializer(), dto)
//        val serializedString = jsonConfig.encodeToString(MpMessage.serializer(), dto)
        println(serializedString)
        assertTrue { serializedString.contains(Regex("stubCase\":\\s*\"SUCCESS")) }
        val deserializedDto = json.decodeFromString(MpRequestDemandCreate.serializer(), serializedString)
        assertEquals(MpRequestDemandCreate.StubCase.SUCCESS, deserializedDto.debug?.stubCase)
    }

}
