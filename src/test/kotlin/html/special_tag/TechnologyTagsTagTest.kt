package html.special_tag

import akjaw.HTML.HTMLBuilder
import akjaw.html.special_tag.TechnologyTagsTag
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.google.common.truth.Truth
import org.junit.Test

class TechnologyTagsTagTest{

    @Test
    fun `has the correct signature`(){
        val technologyTags = TechnologyTagsTag()

        Truth.assertThat(technologyTags.signature).isEqualTo("technologyTags")
    }

    @Test
    fun `isSpecialTag works correctly`(){
        val technologyTags = TechnologyTagsTag()
        Truth.assertThat(technologyTags.isSpecialTag("technologyTags")).isTrue()
        Truth.assertThat(technologyTags.isSpecialTag("technologyTagss")).isFalse()
        Truth.assertThat(technologyTags.isSpecialTag("technologyTags-1")).isTrue()
        Truth.assertThat(technologyTags.isSpecialTag("technologyTags-class")).isTrue()
    }

    @Test
    fun `createTag builds a div with technology div children`(){
        val technologyTags = TechnologyTagsTag()

        val tag = HTMLBuilder.html {  }

        val items = listOf("first", "second", "third")
        val data = JsonArray(items)

        technologyTags.createTag(tag, data)
        val div = tag.tagList[0]
        Truth.assertThat(div.name).isEqualTo("div")

        Truth.assertThat(div.tagList).hasSize(3)

        div.tagList.forEach { divChild ->
            Truth.assertThat(divChild.name).isEqualTo("div")
        }
    }

    @Test
    fun `createTag always gives technology-tags class to the container`(){
        val technologyTags = TechnologyTagsTag()

        val items = JsonArray(listOf("first", "second", "third"))
        var tag = HTMLBuilder.html {  }.apply {
            technologyTags.createTag(this, items)
        }

        Truth.assertThat(tag.tagList[0].className).isEqualTo("technology-tags")

        tag = HTMLBuilder.html {  }.apply {
            technologyTags.createTag(this, items, "class")
        }

        Truth.assertThat(tag.tagList[0].className).contains("technology-tags")
    }


    @Test
    fun `createTag correctly creates language tags`(){
        val technologyTags = TechnologyTagsTag()

        val tag = HTMLBuilder.html {  }

        val languageObject = JsonObject(mapOf("en" to "JavaScript", "pl" to "DżawaSkrypt"))
        val items = listOf("first", languageObject, "third")
        val data = JsonArray(items)

        technologyTags.createTag(tag, data)

        val divContainer = tag.tagList[0]

        Truth.assertThat(divContainer.tagList).hasSize(4)

        Truth.assertThat(divContainer.tagList[0].textContent).isEqualTo("first")

        Truth.assertThat(divContainer.tagList[1].textContent).isEqualTo("JavaScript")

        Truth.assertThat(divContainer.tagList[2].textContent).isEqualTo("DżawaSkrypt")

        Truth.assertThat(divContainer.tagList[3].textContent).isEqualTo("third")
    }
}