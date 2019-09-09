package html

import akjaw.HTML.*
import com.google.common.truth.Truth
import org.junit.Test
import java.lang.NullPointerException

class HtmlBuilderTest {

    @Test
    fun `correctly builds root`() {
        //Given that the user creates an empty html tree
        val html = HTMLBuilder.html { }

        //Then toString correctly outputs only one html tag
        Truth.assertThat(html.toString()).isEqualTo("<html></html>")
    }

    @Test
    fun `textContent keeps it's format`() {
        //Given that the user creates an empty html tree
        val html = HTMLBuilder.html {
            +"Text That has Different CAPS"
        }

        //Then toString correctly outputs only one html tag
        Truth.assertThat(html.textContent).isEqualTo("Text That has Different CAPS")
    }

    @Test
    fun `correctly nests tags`() {
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
        Truth.assertThat(html.toString().replace(" ", "")).isEqualTo("<html><div><div></div></div></html>")
    }

    @Test
    fun `correctly adds style`() {
        //Given that the user adds style to a node
        val html = HTMLBuilder.html {
            tag("div") {
                +Style(
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
        Truth.assertThat(html.toString().replace(" ", ""))
            .isEqualTo("""<html><divstyle="color:black;background:red"></div></html>""")
    }

    @Test
    fun `correctly adds attributes`() {
        //Given that the user adds two attributes to a tag
        val html = HTMLBuilder.html {
            tag("img") {
                +Attributes(
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

        Truth.assertThat(html.toString().replace(" ", ""))
            .isEqualTo("""<html><imgsrc="1.jpg"alt="image"></img></html>""")
    }

    @Test
    fun `toString output has correct format`() {
        //Given that the user creates a tree with nested nodes with style and attributes
        val html = HTMLBuilder.html {
            tag("div") {
                +Style(
                    "color" to "red"
                )
                tag("div") {
                    +Attributes(
                        "src" to "1.jpg",
                        "alt" to "image"
                    )
                }
            }
        }

        //Then toString correctly formats the tree
        Truth.assertThat(html.toString())
            .isEqualTo("""<html><div style="color:red"><div src="1.jpg" alt="image"></div></div></html>""")
    }

    @Test
    fun `getByName returns list of tags that are in that tree`() {
        val html = HTMLBuilder.html {
            tag("div") {
                tag("span") {
                    +"parent"
                    tag("span") {
                        +"child"
                    }
                }
                tag("span") {
                    +"second"
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
    fun `getByName returns null if no elements found in that tree`() {
        val html = HTMLBuilder.html()

        Truth.assertThat(html.getByName("ul")).isNull()
    }

    @Test
    fun `getByName returns tags in correct order`() {
        val html = HTMLBuilder.html {
            tag("div") {
                tag("span") {
                    +"parent"
                    tag("span") {
                        +"child"
                    }
                }
                tag("span") {
                    +"second"
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
    fun `getByName correctly works with nested calls`() {
        val html = HTMLBuilder.html {
            tag("div") {
                +"first div"
                tag("span") {
                    +"first span"
                    tag("div") {
                        +"second div"
                        tag("span") {
                            +"second span"
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
    fun `getByClass returns list of tags with class that are in that tree`() {
        val html = HTMLBuilder.html {
            tag("div") {
                +"root"
                +Attributes("class" to "find")
                tag("span") {
                    +"parent"
                    tag("span") {
                        +"with class"
                        +Attributes("class" to "find")
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
    fun `getByClass returns null if no tags with class are in that tree`() {
        val html = HTMLBuilder.html {

        }

        val foundSpan = html.getByClass("find")
        Truth.assertThat(foundSpan).isNull()
    }

    @Test
    fun `parent property has the correct tag reference`() {
        val html = HTMLBuilder.html {
            tag("div") {
                tag("span")
                tag("p")
            }
        }

        val div = html.tagList[0]
        val span = html.tagList[0].tagList[0]
        val p = html.tagList[0].tagList[1]
        Truth.assertThat(span.parent).isEqualTo(div)
        Truth.assertThat(p.parent).isEqualTo(div)
        Truth.assertThat(div.parent).isEqualTo(html)
    }


    @Test
    fun `insertAt correctly inserts tag at given index`() {
        val html = HTMLBuilder.html {}

        val tag1 = Tag("div")
        html.insertAt(0, tag1)
        Truth.assertThat(html.tagList[0]).isEqualTo(tag1)

        val tag2 = Tag("span")
        html.insertAt(1, tag2)
        Truth.assertThat(html.tagList[1]).isEqualTo(tag2)

        val tag3 = Tag("p")
        html.insertAt(0, tag3)
        Truth.assertThat(html.tagList[0]).isEqualTo(tag3)
        Truth.assertThat(html.tagList[1]).isEqualTo(tag1)
        Truth.assertThat(html.tagList[2]).isEqualTo(tag2)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `insertAt throws outOfBound error if index is not correct`() {
        val html = HTMLBuilder.html {
            tag("div")
        }

        html.insertAt(2, Tag("div"))
    }

    @Test
    fun `append always inserts the tag at the last index`() {
        val html = HTMLBuilder.html {}

        val tag1 = Tag("div")
        html.append(tag1)
        Truth.assertThat(html.tagList[0]).isEqualTo(tag1)

        val tag2 = Tag("span")
        html.append(tag2)
        Truth.assertThat(html.tagList[1]).isEqualTo(tag2)

        val tag3 = Tag("p")
        html.append(tag3)
        Truth.assertThat(html.tagList[2]).isEqualTo(tag3)
    }

    @Test
    fun `prepend always inserts the tag at the first index`() {
        val html = HTMLBuilder.html {}

        val tag1 = Tag("div")
        html.prepend(tag1)
        Truth.assertThat(html.tagList[0]).isEqualTo(tag1)

        val tag2 = Tag("span")
        html.prepend(tag2)
        Truth.assertThat(html.tagList[0]).isEqualTo(tag2)
        Truth.assertThat(html.tagList[1]).isEqualTo(tag1)

        val tag3 = Tag("p")
        html.prepend(tag3)
        Truth.assertThat(html.tagList[0]).isEqualTo(tag3)
        Truth.assertThat(html.tagList[1]).isEqualTo(tag2)
        Truth.assertThat(html.tagList[2]).isEqualTo(tag1)
    }

    @Test
    fun `addClass correctly adds the specified class`() {
        val html = HTMLBuilder.html {
            tag("div") {
                tag("span") {

                }
            }
        }

        val div = html.tagList[0]
        val span = div.tagList[0]

        Truth.assertThat(div.className).isNull()
        Truth.assertThat(span.className).isNull()

        div.addClass("first")
        Truth.assertThat(div.className).isEqualTo("first")

        div.addClass("second")
        Truth.assertThat(div.className).isEqualTo("first second")


        span.addClass("   third   ")
        Truth.assertThat(span.className).isEqualTo("third")

        span.addClass("fourth    fifth   ")
        Truth.assertThat(span.className).isEqualTo("third fourth fifth")
    }

    @Test
    fun `removeClass correctly removes the specified class`() {
        val html = HTMLBuilder.html {
            tag("div") {
                + Attributes("class" to "first second")
                tag("span") {
                    + Attributes("class" to "third fourth fifth")
                }
            }
        }

        val div = html.tagList[0]
        val span = div.tagList[0]

        Truth.assertThat(div.className).isEqualTo("first second")
        Truth.assertThat(span.className).isEqualTo("third fourth fifth")

        div.removeClass("first")
        Truth.assertThat(div.className).isEqualTo("second")

        div.removeClass("second")
        Truth.assertThat(div.className).isNull()


        span.removeClass("fourth")
        Truth.assertThat(span.className).isEqualTo("third fifth")

        span.removeClass("fifth")
        Truth.assertThat(span.className).isEqualTo("third")

    }

    @Test
    fun `addClass correctly updates tag attributes`() {
        val html = HTMLBuilder.html {
            tag("div") {
            }
        }

        val div = html.tagList[0]

        Truth.assertThat(div.className).isNull()

        div.addClass("first")
        Truth.assertThat(div.attributes).hasSize(1)
        Truth.assertThat(div.attributes[0]).isEqualTo(Attribute("class", "first"))

        div.addClass("second")
        Truth.assertThat(div.attributes).hasSize(1)
        Truth.assertThat(div.attributes[0]).isEqualTo(Attribute("class", "first second"))
    }

    @Test
    fun `removeClass correctly updates tag attributes`() {
        val html = HTMLBuilder.html {
            tag("div") {
                + Attributes("class" to "first second")
            }
        }

        val div = html.tagList[0]

        Truth.assertThat(div.attributes[0].value).isEqualTo("first second")

        div.removeClass("first")
        Truth.assertThat(div.attributes[0].value).isEqualTo("second")

        div.removeClass("second")
        Truth.assertThat(div.attributes).hasSize(0)

    }
}

//git add . && git commit -m 'added replace and remove to Attributes / HtmlBuilder addClass and removeClass now correctly updates Attributes'
