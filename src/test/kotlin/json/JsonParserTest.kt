package json

import akjaw.JsonParser
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonBase
import com.beust.klaxon.JsonObject
import com.beust.klaxon.KlaxonException
import com.google.common.truth.Truth
import org.junit.Test
import java.io.FileNotFoundException

class JsonParserTest{

    @Test
    fun `correct parse returns Success that includes the JsonBase`(){
        val result = JsonParser.parse("src/test/resources/json/array.json")

        Truth.assertThat(result.isSuccess).isTrue()

        Truth.assertThat(result.getOrNull()).isNotNull()
        Truth.assertThat(result.getOrNull() is JsonBase).isTrue()
    }

    @Test
    fun `when the file doesnt exist parsing returns a Failure`(){
        val result = JsonParser.parse("doesnt_exist.txt")

        Truth.assertThat(result.isFailure).isTrue()

        Truth.assertThat(result.exceptionOrNull()).isNotNull()
        Truth.assertThat(result.exceptionOrNull() is FileNotFoundException).isTrue()

    }

    @Test
    fun `when the file doesnt have the correct format parsing returns a Failure`(){
        val result = JsonParser.parse("src/test/resources/json/not_json.txt")

        Truth.assertThat(result.isFailure).isTrue()

        Truth.assertThat(result.exceptionOrNull()).isNotNull()
        Truth.assertThat(result.exceptionOrNull() is KlaxonException).isTrue()
    }


    @Test
    fun `correctly parses json that has an array root`(){
        val result = JsonParser.parse("src/test/resources/json/array.json")

        val jsonBase = result.getOrThrow()

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
        val result = JsonParser.parse("src/test/resources/json/object.json")

        val jsonBase = result.getOrThrow()

        Truth.assertThat(jsonBase is JsonObject).isTrue()

        val jsonObject = jsonBase as JsonObject

        Truth.assertThat(jsonObject["name"]).isEqualTo("Adam")
        Truth.assertThat(jsonObject["age"]).isEqualTo(15)
    }

}