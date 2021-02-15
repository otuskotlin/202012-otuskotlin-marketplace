package ru.otus.otuskotlin.marketplace.transport.kmp.models

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import ru.otus.otuskotlin.marketplace.transport.kmp.models.common.MpMessage
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpDemandCreateDto
import ru.otus.otuskotlin.marketplace.transport.kmp.models.demands.MpRequestDemandCreate
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SerializationTest {

    @Test
    fun requestSerializeTest(){
        val request: MpMessage = MpRequestDemandCreate(
            createData = MpDemandCreateDto(
                title = "demand1",
                tagIds = setOf("123")
            )
        )

        val json = Json {
            prettyPrint = true
            serializersModule = SerializersModule {
                polymorphic(MpMessage::class){
                    subclass(MpRequestDemandCreate::class)
                }
                classDiscriminator = "type"
            }
        }

        val serialString = json.encodeToString(MpMessage.serializer(), request)
        val dto = json.decodeFromString(MpMessage.serializer(), serialString)

        assertEquals("demand1", (dto as? MpRequestDemandCreate)?.createData?.title)
    }
}
