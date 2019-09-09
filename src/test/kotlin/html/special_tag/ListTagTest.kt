package html.special_tag

import akjaw.HTML.HTMLBuilder
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.google.common.truth.Truth
import org.junit.Test

class ListTagTest{

    @Test
    fun `has the signature is correct`(){
        val listTag = ListTag()
        Truth.assertThat(listTag.signature).isEqualTo("list")
    }

    @Test
    fun `isSpecialTag works correctly`(){
        val listTag = ListTag()
        Truth.assertThat(listTag.isSpecialTag("list")).isTrue()
        Truth.assertThat(listTag.isSpecialTag("lists")).isFalse()
        Truth.assertThat(listTag.isSpecialTag("list-1")).isTrue()
        Truth.assertThat(listTag.isSpecialTag("list-class")).isTrue()
    }

    @Test
    fun `createTag builds an ul tag with li children`(){
        val listTag = ListTag()

        val tag = HTMLBuilder.html {  }

        val items = listOf("one", "two", "three")
        val data = JsonArray(items)

        listTag.createTag(tag, data)
        val ul = tag.tagList[0]
        Truth.assertThat(ul.name).isEqualTo("ul")

        Truth.assertThat(ul.tagList).hasSize(3)

        ul.tagList.forEach { liChild ->
            Truth.assertThat(liChild.name).isEqualTo("li")
        }
    }

    @Test
    fun `createTag items have the correct text content`(){
        val listTag = ListTag()

        val tag = HTMLBuilder.html {  }

        val items = listOf("one", "two", "three")
        val data = JsonArray(items)

        listTag.createTag(tag, data)

        val ul = tag.tagList[0]

        Truth.assertThat(ul.tagList).hasSize(3)

        ul.tagList.forEachIndexed { index, liChild ->
            Truth.assertThat(liChild.textContent).isEqualTo(items[index])
        }

    }


    @Test
    fun `createTag gives the class to the ul`(){
        val listTag = ListTag()

        val tag = HTMLBuilder.html {  }

        val items = listOf("one", "two", "three")
        val data = JsonArray(items)

        listTag.createTag(tag, data, "class")

        val ul = tag.tagList[0]

        Truth.assertThat(ul.className).isEqualTo("class")
    }


    //TODO translation
    @Test
    fun `createTag correctly creates language tags`(){
        val listTag = ListTag()

        val tag = HTMLBuilder.html {  }

        val languageObject = JsonObject(mapOf("en" to "twenty", "pl" to "dwadziescia"))
        val items = listOf("one", languageObject, "three")
        val data = JsonArray(items)

        listTag.createTag(tag, data)

        val ul = tag.tagList[0]

        Truth.assertThat(ul.tagList).hasSize(4)

        Truth.assertThat(ul.tagList[0].textContent).isEqualTo("one")

        Truth.assertThat(ul.tagList[1].textContent).isEqualTo("twenty")

        Truth.assertThat(ul.tagList[2].textContent).isEqualTo("dwadziescia")

        Truth.assertThat(ul.tagList[3].textContent).isEqualTo("three")


    }

}