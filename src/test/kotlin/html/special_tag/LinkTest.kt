package html.special_tag

import html.HTMLBuilder
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.google.common.truth.Truth
import html.Attribute
import html.Tag
import org.junit.Test
import java.lang.IllegalArgumentException

class LinkTest{

    private fun createLink(arguments: Map<String, String>): Tag {
        val linkTag = LinkTag()

        val tag = HTMLBuilder.html {  }

        val data = JsonObject(arguments)

        linkTag.createTag(tag, data)

        return tag
    }

    @Test
    fun `has the signature is correct`(){
        val galleryTag = LinkTag()
        Truth.assertThat(galleryTag.signature).isEqualTo("link")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `throws an error if text-en is not present`(){
        val arguments = mapOf("href" to "test.com", "text" to "Test")
        val html = createLink(arguments)
    }

    @Test
    fun `isSpecialTag works correctly`(){
        val linkTag = LinkTag()
        Truth.assertThat(linkTag.isSpecialTag("link")).isTrue()
        Truth.assertThat(linkTag.isSpecialTag("links")).isFalse()
        Truth.assertThat(linkTag.isSpecialTag("link-1")).isTrue()
        Truth.assertThat(linkTag.isSpecialTag("link-class")).isTrue()
    }

    @Test
    fun `creates an anchor`(){
        val arguments = mapOf("href" to "test.com", "text" to "Test", "text-en" to "one")
        val html = createLink(arguments)

        val link = html.tagList[0]
        Truth.assertThat(link.name).isEqualTo("a")
    }

    @Test
    fun `the anchor has the correct text content`(){
        val arguments = mapOf("href" to "test.com", "text-en" to "one", "text-pl" to "jeden")
        val html = createLink(arguments)

        val link = html.tagList[0]

        val englishNode = link.tagList[0]
        Truth.assertThat(englishNode.textContent).contains(arguments.getValue("text-en"))

        val polishNode = link.tagList[1]
        Truth.assertThat(polishNode.textContent).contains(arguments.getValue("text-pl"))
    }

    @Test
    fun `if the polish anchor text is empty then it uses the english one`(){
        val arguments = mapOf("href" to "test.com", "text-en" to "one")
        val html = createLink(arguments)

        val link = html.tagList[0]

        val englishNode = link.tagList[0]
        Truth.assertThat(englishNode.textContent).contains(arguments.getValue("text-en"))

        val polishNode = link.tagList[1]
        Truth.assertThat(polishNode.textContent).contains(arguments.getValue("text-en"))
    }

    @Test
    fun `the anchor creates a span that holds the text content`(){
        val arguments = mapOf("href" to "test.com", "text-en" to "one", "text-pl" to "jeden")
        val html = createLink(arguments)

        val link = html.tagList[0]

        val englishNode = link.tagList[0]
        Truth.assertThat(englishNode.name).isEqualTo("span")

        val polishNode = link.tagList[1]
        Truth.assertThat(polishNode.name).isEqualTo("span")
    }

    @Test
    fun `the anchor has the correct href of the link`(){
        val arguments = mapOf("href" to "test.com", "text-en" to "one")
        val html = createLink(arguments)

        val link = html.tagList[0]
        val href = Attribute("href", arguments.getValue("href"))
        Truth.assertThat(link.attributes).contains(href)
    }

    @Test
    fun `the anchor by default has target _blank`(){
        val arguments = mapOf("href" to "test.com", "text-en" to "one")
        val html = createLink(arguments)

        val link = html.tagList[0]
        val href = Attribute("target", "_blank")
        Truth.assertThat(link.attributes).contains(href)
    }
}