package html.special_tag

import html.HTMLBuilder
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.google.common.truth.Truth
import html.Attribute
import html.Tag
import org.junit.Test
import java.lang.IllegalArgumentException

class IconTest{

    private fun createIcon(src: String): Tag {
        val iconTag = IconTag()

        val tag = HTMLBuilder.html {  }

        val data = src

        iconTag.createTag(tag, data)

        return tag
    }

    @Test
    fun `has the signature is correct`(){
        val iconTag = IconTag()
        Truth.assertThat(iconTag.signature).isEqualTo("icon")
    }

    @Test
    fun `creates an img tag`(){
        val iconTag = createIcon("aa").tagList[0]
        Truth.assertThat(iconTag.name).isEqualTo("img")
    }

    @Test
    fun `the img tag has the correct src`(){
        val iconTag = createIcon("aa").tagList[0]
        Truth.assertThat(iconTag.attributes).contains(Attribute("src", "aa"))
    }


    @Test
    fun `gives the that the correct class`(){
        val iconTag = createIcon("aa").tagList[0]
        Truth.assertThat(iconTag.className).contains("project-icon")
    }
}