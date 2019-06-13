package JSON

import akjaw.JsonParser
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonBase
import com.beust.klaxon.JsonObject
import com.beust.klaxon.KlaxonException
import com.google.common.io.Resources
import com.google.common.truth.Truth
import org.junit.Test
import sun.misc.IOUtils
import java.nio.charset.StandardCharsets
import java.io.File
import java.lang.NullPointerException

//TODO delete later?
class JsonParserTest{

    @Test
    fun `correctly parses json that has an array root`(){
        val jsonBase = JsonParser.from("src/test/resources/json/array.json")

        Truth.assertThat(jsonBase is JsonArray<*>).isTrue()

        val jsonArray  = jsonBase as JsonArray<*>
        Truth.assertThat(jsonArray[0] is JsonObject).isTrue()
        Truth.assertThat(jsonArray[1] is JsonObject).isTrue()

        val first = jsonArray[0] as JsonObject
        Truth.assertThat(first["name"]).isEqualTo("Adam")
        val second = jsonArray[1] as JsonObject
        Truth.assertThat(second["name"]).isEqualTo("Bob")
    }

    @Test
    fun `correctly parses json that has an object root`(){
        val jsonBase = JsonParser.from("src/test/resources/json/object.json")

        Truth.assertThat(jsonBase is JsonObject).isTrue()

        val jsonObject = jsonBase as JsonObject

        Truth.assertThat(jsonObject["name"]).isEqualTo("Adam")
        Truth.assertThat(jsonObject["age"]).isEqualTo(15)
    }

    @Test(expected = KlaxonException::class)
    fun `throws IllegalArgumentException when json file is not correct`(){
        val jsonBase = JsonParser.from("src/test/resources/json/not_json.txt")
    }

}