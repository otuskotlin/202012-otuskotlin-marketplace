package ru.otus.otuskotlin.marketplace.backend.transport.openapi

import ru.otus.otuskotlin.marketplace.transport.openapi.demand.models.BaseMessage
import ru.otus.otuskotlin.marketplace.transport.openapi.demand.models.MpDemandListFilter
import ru.otus.otuskotlin.marketplace.transport.openapi.demand.models.MpRequestDemandList
import kotlin.test.Test
import kotlin.test.assertTrue

class SerializationTest {
    @Test
    fun simpleSerialization() {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(MpRequestDemandList::class.java)
        val request = MpRequestDemandList(
            requestId = "123",
            onResponse = "234",
            filterData = MpDemandListFilter(text = "fil"),
        )
        val json = adapter.toJson(request)
        println("JSON: $json")
        assertTrue { json.contains(Regex("""\s*"onResponse":\s*"234"\s*""")) }
    }

    @Test
    fun polymorphSerialization() {
        val moshi = Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory
                    .of(BaseMessage::class.java, "")
                    .withSubtype(MpRequestDemandList::class.java, "occupation")
                    .withSubtype(PolicePerson.java, "rank")
            )
            .build()
        val adapter = moshi.adapter(MpRequestDemandList::class.java)
        val request = MpRequestDemandList(
            requestId = "123",
            onResponse = "234",
            filterData = MpDemandListFilter(text = "fil"),
        )
        val json = gson.toJson(request)
        println("JSON: $json")
        assertTrue { json.contains(Regex("""\s*"onResponse":\s*"234"\s*""")) }
    }
}
