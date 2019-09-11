package html

import akjaw.HTML.HTMLBuilder
import akjaw.HTML.Tag
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.google.common.truth.Truth
import org.junit.Test

class TagBuilderTest {
    fun initializeCreateTagWithLanguages(
        tagName: String = "div",
        englishText: String = "english",
        polishText: String = "polish",
        classes: String? = null
    ): Tag {
        val html = HTMLBuilder.html { }

        TagBuilder.createTagWithLanguages(
            html,
            tagName,
            englishText,
            polishText,
            {},
            classes
        )

        return html
    }

//    fun initializeCreateLanguageTagFromJsonObject(
//        tagName: String = "div",
//        englishText: String = "english",
//        polishText: String = "polish",
//        classes: String? = null
//    ): Tag {
//        val html = HTMLBuilder.html { }
//
//        TagBuilder.createLanguageTagFromJsonObject(
//            html,
//            tagName,
//            s,
//            {}
//        )
//
//        return html
//    }

    @Test
    fun `createTagWithLanguages creates two tags with language classes`(){
        val html = initializeCreateTagWithLanguages()

        Truth.assertThat(html.tagList).hasSize(2)
        val enDiv = html.tagList[0]
        Truth.assertThat(enDiv.className).contains("en")

        val plDiv = html.tagList[1]
        Truth.assertThat(plDiv.className).contains("pl")
    }

    @Test
    fun `createTagWithLanguages creates the same two tags`(){
        val html = initializeCreateTagWithLanguages()

        Truth.assertThat(html.tagList).hasSize(2)
        val enDiv = html.tagList[0]
        Truth.assertThat(enDiv.name).contains("div")

        val plDiv = html.tagList[1]
        Truth.assertThat(plDiv.name).contains("div")
    }

    @Test
    fun `createTagWithLanguages tags has the correct corresponding text`(){
        val html = initializeCreateTagWithLanguages()

        Truth.assertThat(html.tagList).hasSize(2)
        val enDiv = html.tagList[0]
        Truth.assertThat(enDiv.textContent).isEqualTo("english")

        val plDiv = html.tagList[1]
        Truth.assertThat(plDiv.textContent).isEqualTo("polish")
    }

    @Test
    fun `createTagWithLanguages polish tag has the class none`(){
        val html = initializeCreateTagWithLanguages()

        Truth.assertThat(html.tagList).hasSize(2)

        val plDiv = html.tagList[1]
        Truth.assertThat(plDiv.className).contains("none")
    }


    @Test
    fun `when specifying classes in createTagWithLanguages it adds the class to both tags`(){
        val html = initializeCreateTagWithLanguages(classes = "test")

        Truth.assertThat(html.tagList).hasSize(2)
        val enDiv = html.tagList[0]
        Truth.assertThat(enDiv.className).contains("test")

        val plDiv = html.tagList[1]
        Truth.assertThat(plDiv.className).contains("test")
    }


    @Test
    fun `createLanguageTagFromJsonObject correctly parses jsonObject and creates tags with their corresponding language class`(){
        val html = HTMLBuilder.html { }

        val jsonObject= JsonObject(mapOf("en" to "one", "pl" to "jeden"))
        TagBuilder.createLanguageTagFromJsonObject(
            html,
            "div",
            jsonObject
        )

        Truth.assertThat(html.tagList).hasSize(2)
        val enDiv = html.tagList[0]
        Truth.assertThat(enDiv.className).contains("en")

        val plDiv = html.tagList[1]
        Truth.assertThat(plDiv.className).contains("pl")
    }

    @Test
    fun `createLanguageTagFromJsonObject correctly parses jsonObject and creates tags with the correct text`(){
        val html = HTMLBuilder.html { }

        val jsonObject= JsonObject(mapOf("en" to "one", "pl" to "jeden"))
        TagBuilder.createLanguageTagFromJsonObject(
            html,
            "div",
            jsonObject
        )

        Truth.assertThat(html.tagList).hasSize(2)
        val enDiv = html.tagList[0]
        Truth.assertThat(enDiv.textContent).contains("one")

        val plDiv = html.tagList[1]
        Truth.assertThat(plDiv.textContent).contains("jeden")
    }

    @Test
    fun `createTagsFromFlatJsonArray correctly creates items for simple string array`(){
        val html = HTMLBuilder.html { }

        val jsonArray= JsonArray("one", "two", "three")
        TagBuilder.createTagsFromFlatJsonArray(
            html,
            jsonArray,
            "div"
        )

        Truth.assertThat(html.tagList).hasSize(3)

        Truth.assertThat(html.tagList[0].textContent).contains("one")

        Truth.assertThat(html.tagList[1].textContent).contains("two")

        Truth.assertThat(html.tagList[2].textContent).contains("three")
    }

    @Test
    fun `createTagsFromFlatJsonArray correctly creates items for language object array`(){
        val html = HTMLBuilder.html { }

        val jsonArray= JsonArray(
            JsonObject(mapOf("en" to "one", "pl" to "jeden")),
            JsonObject(mapOf("en" to "two", "pl" to "dwa")),
            JsonObject(mapOf("en" to "three", "pl" to "trzy"))
        )
        TagBuilder.createTagsFromFlatJsonArray(
            html,
            jsonArray,
            "div"
        )

        Truth.assertThat(html.tagList).hasSize(3 * 2)

        Truth.assertThat(html.tagList[0].textContent).contains("one")
        Truth.assertThat(html.tagList[1].textContent).contains("jeden")

        Truth.assertThat(html.tagList[2].textContent).contains("two")
        Truth.assertThat(html.tagList[3].textContent).contains("dwa")

        Truth.assertThat(html.tagList[4].textContent).contains("three")
        Truth.assertThat(html.tagList[5].textContent).contains("trzy")
    }
}
