package html.helpers

import com.google.common.truth.Truth
import org.junit.Test
import java.lang.IllegalArgumentException

class JsonHelpersTest{

    @Test
    fun `parseJsonKey returns a className of null if it is not specified in the tag string`(){
        val tagName = "name"
        val jsonTagData = JsonHelpers.parseJsonKey("name")

        Truth.assertThat(jsonTagData.className).isNull()
    }

    @Test
    fun `parseJsonKey returns a className of null if the tag string contains a number after the dash`(){
        var jsonTagData = JsonHelpers.parseJsonKey("name-1")
        Truth.assertThat(jsonTagData.className).isNull()

        jsonTagData = JsonHelpers.parseJsonKey("name-121")
        Truth.assertThat(jsonTagData.className).isNull()
    }

    @Test
    fun `parseJsonKey returns a className which value is equal to the string after the dash`(){
        var jsonTagData = JsonHelpers.parseJsonKey("name-class")
        Truth.assertThat(jsonTagData.className).isEqualTo("class")

        jsonTagData = JsonHelpers.parseJsonKey("name-second")
        Truth.assertThat(jsonTagData.className).isEqualTo("second")

        jsonTagData = JsonHelpers.parseJsonKey("name-s2")
        Truth.assertThat(jsonTagData.className).isEqualTo("s2")
    }


    @Test(expected = IllegalArgumentException::class)
    fun `parseJsonKey throws an exception when the dash is malformed`(){
        val jsonTagData = JsonHelpers.parseJsonKey("name--class")
        Truth.assertThat(jsonTagData.className).isEqualTo("class")
    }
}