package ru.otus.otuskotlin.marketplace.transport.kmp.models

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.TechDetsDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpDemandCreateDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandCreate
import kotlin.test.Test
import kotlin.test.assertEquals

class SerializationTest {

    @Test
    fun serializeMpRequestDemandCreateTest(){
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
            )
        )

        val serializedString = json.encodeToString(MpRequestDemandCreate.serializer(), dto)
        println(serializedString)
        val deserializedDto = json.decodeFromString(MpRequestDemandCreate.serializer(), serializedString)
        assertEquals("tech-det-id", deserializedDto.createData?.techDets?.firstOrNull()?.id)
    }

    @Test
    fun serializeMpRequestTest(){
        val jsonRequest = Json {
            prettyPrint = true
            serializersModule = SerializersModule {
                polymorphic(MpMessage::class) {
                    subclass(MpRequestDemandCreate::class, MpRequestDemandCreate.serializer())
                }

            }
            classDiscriminator = "type"
        }
        val dto:MpMessage = MpRequestDemandCreate(
            requestId = "create-id",
            startTime = "2021-02-13T12:00:00",
            createData = MpDemandCreateDto(
                title = "demand-2",
                description = "some description",
                techDets = setOf(TechDetsDto(
                    id = "tech-det-id"
                ))
            )
        )
        val serializedString = jsonRequest.encodeToString(dto)
        println(serializedString)
        val deserializedDto = jsonRequest.decodeFromString(MpMessage.serializer(), serializedString)
        assertEquals("tech-det-id", (deserializedDto as? MpRequestDemandCreate)?.createData?.techDets?.firstOrNull()?.id)
    }

}
