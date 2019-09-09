package HTML.special_tag

import akjaw.HTML.HTMLBuilder
import akjaw.HTML.Tag
import akjaw.HTML.TagBuilder
import akjaw.Model.Project
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import java.io.File
import java.lang.NullPointerException

class SpecialTagBuilderTest{
    fun initializeHtml(
        tagName: String = "div",
        englishText: String = "english",
        polishText: String = "polish",
        classes: String? = null
    ): Tag {
        val html = HTMLBuilder.html {  }

        TagBuilder.createTagWithLanguages(
            html,
            tagName,
            englishText,
            polishText,
            classes
        )

        return html
    }

    @Test
    fun `createTagWithLanguages creates two tags with language classes`(){
        val html = initializeHtml()

        Truth.assertThat(html.tagList).hasSize(2)
        val enDiv = html.tagList[0]
        Truth.assertThat(enDiv.className).contains("en")

        val plDiv = html.tagList[1]
        Truth.assertThat(plDiv.className).contains("pl")
    }

    @Test
    fun `createTagWithLanguages creates the same two tags`(){
        val html = initializeHtml()

        Truth.assertThat(html.tagList).hasSize(2)
        val enDiv = html.tagList[0]
        Truth.assertThat(enDiv.name).contains("div")

        val plDiv = html.tagList[1]
        Truth.assertThat(plDiv.name).contains("div")
    }

    @Test
    fun `createTagWithLanguages tags has the correct corresponding text`(){
        val html = initializeHtml()

        Truth.assertThat(html.tagList).hasSize(2)
        val enDiv = html.tagList[0]
        Truth.assertThat(enDiv.textContent).isEqualTo("english")

        val plDiv = html.tagList[1]
        Truth.assertThat(plDiv.textContent).isEqualTo("polish")
    }

    @Test
    fun `createTagWithLanguages polish tag has the class none`(){
        val html = initializeHtml()

        Truth.assertThat(html.tagList).hasSize(2)

        val plDiv = html.tagList[1]
        Truth.assertThat(plDiv.className).contains("none")
    }


    @Test
    fun `when specifying classes in createTagWithLanguages it adds the class to both tags`(){
        val html = initializeHtml(classes = "test")

        Truth.assertThat(html.tagList).hasSize(2)
        val enDiv = html.tagList[0]
        Truth.assertThat(enDiv.className).contains("test")

        val plDiv = html.tagList[1]
        Truth.assertThat(plDiv.className).contains("test")
    }
}
