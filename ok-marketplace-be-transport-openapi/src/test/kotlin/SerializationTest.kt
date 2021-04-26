import com.fasterxml.jackson.databind.ObjectMapper
import ru.otus.otuskotlin.marketplace.transport.openapi.demand.models.BaseMessage
import ru.otus.otuskotlin.marketplace.transport.openapi.demand.models.MpRequestDemandList
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class SerializationTest {
    @Test
    fun simpleSerialization() {
        val mapper = ObjectMapper()
        val obj = MpRequestDemandList(requestId = "123")
        val jsonStr = mapper.writeValueAsString(obj)
        println("JSON: $jsonStr")
        assertTrue("must contain requestId = 123") {
            jsonStr.contains(Regex("""\s*"requestId":\s*"123"\s*"""))
        }
        assertTrue("must contain type = <class name>") {
            jsonStr.contains(Regex("""\s*"type":\s*"${obj::class.java.simpleName}"\s*"""))
        }

        val deserializedObj = mapper.readValue(jsonStr, BaseMessage::class.java)
        assertEquals(MpRequestDemandList::class.java, deserializedObj::class.java)
        assertEquals(obj, deserializedObj)
    }
}
