package HTML

import akjaw.HTML.Attribute
import akjaw.HTML.Attributes
import akjaw.HTML.HTMLBuilder
import akjaw.HTML.Style
import com.google.common.truth.Truth
import org.junit.Test

class HtmlBuilderTest{

    @Test
    fun `correctly builds root`(){
        //Given that the user creates an empty html tree
        val html = HTMLBuilder.html {  }

        //Then toString correctly outputs only one html tag
        Truth.assertThat(html.toStringWithoutWhitespace()).isEqualTo("<html></html>")
    }

    @Test
    fun `correctly nests tags`(){
        //Given that the user builds a tree with two nested divs
        val html = HTMLBuilder.html {
            tag("div") {
                tag("div")
            }
        }

        //Then the tag have a correct number of children
        Truth.assertThat(html.tagList).hasSize(1)
        Truth.assertThat(html.tagList[0].tagList).hasSize(1)

        //Then the tags have the correct names
        Truth.assertThat(html.name).isEqualTo("html")
        Truth.assertThat(html.tagList[0].name).isEqualTo("div")
        Truth.assertThat(html.tagList[0].tagList[0].name).isEqualTo("div")

        //Then toString correctly outputs the tree
        Truth.assertThat(html.toStringWithoutWhitespace()).isEqualTo("<html><div><div></div></div></html>")
    }

    @Test
    fun `correctly adds style`(){
        //Given that the user adds style to a node
        val html = HTMLBuilder.html {
            tag("div") {
                + Style(
                    "color" to "black",
                    "background" to "red"
                )
            }
        }

        //Then the tag has only one attribute
        Truth.assertThat(html.tagList[0].attributes).hasSize(1)

        //Then the tag has the correct style value
        Truth.assertThat(html.tagList[0].attributes[0])
            .isEqualTo(Attribute("style", "color:black; background:red"))

        //Then toString correctly outputs the tree
        Truth.assertThat(html.toStringWithoutWhitespace())
            .isEqualTo("""<html><divstyle="color:black;background:red"></div></html>""")
    }

    @Test
    fun `correctly adds attributes`(){
        //Given that the user adds two attributes to a tag
        val html = HTMLBuilder.html {
            tag("img") {
                + Attributes(
                    "src" to "1.jpg",
                    "alt" to "image"
                )
            }
        }

        //Then the tag has two attribute
        Truth.assertThat(html.tagList[0].attributes).hasSize(2)

        //Then the attributes have the correct name and values
        Truth.assertThat(html.tagList[0].attributes[0]).isEqualTo(Attribute("src", "1.jpg"))
        Truth.assertThat(html.tagList[0].attributes[1]).isEqualTo(Attribute("alt", "image"))

        Truth.assertThat(html.toStringWithoutWhitespace())
            .isEqualTo("""<html><imgsrc="1.jpg"alt="image"></img></html>""")
    }

    @Test
    fun `toString output has correct format`(){
        //Given that the user creates a tree with nested nodes with style and attributes
        val html = HTMLBuilder.html {
            tag("div") {
                + Style (
                    "color" to "red"
                )
                tag("div") {
                    + Attributes(
                        "src" to "1.jpg",
                        "alt" to "image"
                    )
                }
            }
        }

        //Then toString correctly formats the tree
        Truth.assertThat(html.toString()).isEqualTo("""<html><div style="color:red"><div src="1.jpg" alt="image"></div></div></html>""")
    }

}