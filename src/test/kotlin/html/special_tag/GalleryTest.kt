package html.special_tag

import html.HTMLBuilder
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.google.common.truth.Truth
import html.Attribute
import org.junit.Test

class GalleryTest{

    @Test
    fun `has the signature is correct`(){
        val galleryTag = GalleryTag()
        Truth.assertThat(galleryTag.signature).isEqualTo("gallery")
    }

    @Test
    fun `isSpecialTag works correctly`(){
        val galleryTag = GalleryTag()
        Truth.assertThat(galleryTag.isSpecialTag("gallery")).isTrue()
        Truth.assertThat(galleryTag.isSpecialTag("gallerys")).isFalse()
        Truth.assertThat(galleryTag.isSpecialTag("gallery-1")).isTrue()
        Truth.assertThat(galleryTag.isSpecialTag("gallery-class")).isTrue()
    }

    @Test
    fun `createTag builds a slider`(){
        val galleryTag = GalleryTag()

        val tag = HTMLBuilder.html {  }

        val items = listOf("one", "two", "three")
        val data = JsonArray(items)

        galleryTag.createTag(tag, data)
        val div = tag.tagList[0]
        Truth.assertThat(div.name).isEqualTo("div")
        Truth.assertThat(div.className).contains("slider")
        Truth.assertThat(div.tagList).hasSize(4)
    }


    @Test
    fun `createTag builds the images inside a frame`(){
        val galleryTag = GalleryTag()

        val tag = HTMLBuilder.html {  }

        val items = listOf("one", "two", "three")
        val data = JsonArray(items)

        galleryTag.createTag(tag, data)
        val div = tag.getByClass("frame")

        Truth.assertThat(div!![0].tagList).hasSize(1)

        val slides = div!![0].tagList[0]
        Truth.assertThat(slides.tagList).hasSize(items.size)

        items.forEachIndexed { index, path ->
            val slide = slides.tagList[index]
            val image = slide.tagList[0]
            val attribute = Attribute("src", path)
            Truth.assertThat(image.attributes.contains(attribute)).isTrue()
        }
    }
}