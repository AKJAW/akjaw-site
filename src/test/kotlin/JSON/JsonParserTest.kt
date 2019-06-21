package JSON

import akjaw.JsonParser
import akjaw.Result.Failure
import akjaw.Result.Success
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonBase
import com.beust.klaxon.JsonObject
import com.beust.klaxon.KlaxonException
import com.google.common.truth.Truth
import org.junit.Test

class JsonParserTest{

    @Test
    fun `correct parse returns Success that includes the JsonBase`(){
        val result = JsonParser.parse("src/test/resources/json/array.json")

        Truth.assertThat(result is Success).isTrue()
    }

    @Test
    fun `when the file doesnt exist parsing returns a Failure`(){
        val result = JsonParser.parse("doesnt_exist.txt")

        Truth.assertThat(result is Failure).isTrue()

        Truth.assertThat((result as Failure).errorMessage).isEqualTo("File does not exist")
    }

    @Test
    fun `when the file doesnt have the correct format parsing returns a Failure`(){
        val result = JsonParser.parse("src/test/resources/json/not_json.txt")

        Truth.assertThat(result is Failure).isTrue()

        Truth.assertThat((result as Failure).errorMessage).isEqualTo("Could not parse json")
    }


    @Test
    fun `correctly parses json that has an array root`(){
        val result = JsonParser.parse("src/test/resources/json/array.json")

        val jsonBase = (result as Success).value

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

        val jsonBase = (result as Success).value

        Truth.assertThat(jsonBase is JsonObject).isTrue()

        val jsonObject = jsonBase as JsonObject

        Truth.assertThat(jsonObject["name"]).isEqualTo("Adam")
        Truth.assertThat(jsonObject["age"]).isEqualTo(15)
    }

}