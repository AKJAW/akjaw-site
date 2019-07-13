package HTML

import akjaw.HTML.Attribute
import akjaw.HTML.Attributes
import akjaw.HTML.HTMLBuilder
import akjaw.HTML.Style
import com.google.common.truth.Truth
import org.junit.Test
import java.lang.NullPointerException

class HtmlBuilderTest{

    @Test
    fun `correctly builds root`(){
        //Given that the user creates an empty html tree
        val html = HTMLBuilder.html {  }

        //Then toString correctly outputs only one html tag
        Truth.assertThat(html).isEqualTo("<html></html>")
    }

    @Test
    fun `textContent keeps it's format`(){
        //Given that the user creates an empty html tree
        val html = HTMLBuilder.html {
            + "Text That has Different CAPS"
        }

        //Then toString correctly outputs only one html tag
        Truth.assertThat(html.textContent).isEqualTo("Text That has Different CAPS")
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

    @Test
    fun `getByName returns list of tags that are in that tree`(){
        val html = HTMLBuilder.html {
            tag("div") {
                tag("span"){
                    + "parent"
                    tag("span"){
                        + "child"
                    }
                }
                tag("span") {
                    + "second"
                }
                tag("div")
            }
        }

        val foundSpan = html.getByName("span")
        Truth.assertThat(foundSpan).isNotNull()
        Truth.assertThat(foundSpan).hasSize(3)

        foundSpan ?: throw NullPointerException()

        Truth.assertThat(foundSpan[0].name).isEqualTo("span")
        Truth.assertThat(foundSpan[1].name).isEqualTo("span")
        Truth.assertThat(foundSpan[2].name).isEqualTo("span")
    }

    @Test
    fun `getByName returns null if no elements found in that tree`(){
        val html = HTMLBuilder.html()

        Truth.assertThat(html.getByName("ul")).isNull()
    }

    @Test
    fun `getByName returns tags in correct order`(){
        val html = HTMLBuilder.html {
            tag("div") {
                tag("span"){
                    + "parent"
                    tag("span"){
                        + "child"
                    }
                }
                tag("span") {
                    + "second"
                }
                tag("div")
            }
        }

        val foundSpan = html.getByName("span")

        foundSpan ?: throw NullPointerException()

        Truth.assertThat(foundSpan[0].textContent).isEqualTo("parent")
        Truth.assertThat(foundSpan[1].textContent).isEqualTo("child")
        Truth.assertThat(foundSpan[2].textContent).isEqualTo("second")
    }

    @Test
    fun `getByName correctly works with nested calls`(){
        val html = HTMLBuilder.html {
            tag("div") {
               + "first div"
                tag("span"){
                    + "first span"
                    tag("div"){
                        + "second div"
                        tag("span"){
                            + "second span"
                        }
                    }
                }
            }
        }

        val firstDiv = html.getByName("div")!!.first()
        Truth.assertThat(firstDiv.textContent).isEqualTo("first div")

        val firstSpan = firstDiv.getByName("span")!!.first()
        Truth.assertThat(firstSpan.textContent).isEqualTo("first span")

        val secondDiv = firstSpan.getByName("div")!!.first()
        Truth.assertThat(secondDiv.textContent).isEqualTo("second div")

        val secondSpan = secondDiv.getByName("span")!!.first()
        Truth.assertThat(secondSpan.textContent).isEqualTo("second span")
    }

    @Test
    fun `getByClass returns list of tags with class that are in that tree`(){
        val html = HTMLBuilder.html {
            tag("div") {
                + "root"
                + Attributes("class" to "find")
                tag("span"){
                    + "parent"
                    tag("span"){
                        + "with class"
                        + Attributes("class" to "find")
                    }
                }
                tag("div")
            }
        }

        val foundSpan = html.getByClass("find")
        Truth.assertThat(foundSpan).isNotNull()
        Truth.assertThat(foundSpan).hasSize(2)

        foundSpan ?: throw NullPointerException()

        Truth.assertThat(foundSpan[0].name).isEqualTo("div")
        Truth.assertThat(foundSpan[0].textContent).isEqualTo("root")
        Truth.assertThat(foundSpan[1].name).isEqualTo("span")
        Truth.assertThat(foundSpan[1].textContent).isEqualTo("with class")

    }

    @Test
    fun `getByClass returns null if no tags with class are in that tree`(){
        val html = HTMLBuilder.html {

        }

        val foundSpan = html.getByClass("find")
        Truth.assertThat(foundSpan).isNull()
    }

}