package HTML

import akjaw.HTML.Attribute
import akjaw.HTML.Attributes
import com.google.common.truth.Truth
import org.junit.Test

class AttributesTest{
    @Test
    fun `correctly initializes empty attributes`(){
        //Given that the user initializes empty attributes
        val attributes = Attributes()

        //Then the list should be empty
        Truth.assertThat(attributes).hasSize(0)
    }

    @Test
    fun `correctly initializes attributes with values`(){
        //Given that the user initializes with two values
        val attributes = Attributes(
            "src" to "1.jpg",
            "alt" to "image"
        )

        //Then the list size should be 2
        Truth.assertThat(attributes).hasSize(2)

        //Then the list has the correct values
        Truth.assertThat(attributes[0]).isEqualTo(Attribute("src", "1.jpg"))
        Truth.assertThat(attributes[1]).isEqualTo(Attribute("alt", "image"))
    }

    @Test
    fun `correctly adds a single attribute`(){
        //Given that the user initializes empty attributes and then adds a single attribute
        val attributes = Attributes()
        attributes.add(Attribute("src", "1.jpg"))

        //Then the list size should be 1
        Truth.assertThat(attributes).hasSize(1)

        //Then the list has the correct values
        Truth.assertThat(attributes[0]).isEqualTo(Attribute("src", "1.jpg"))
    }

    @Test
    fun `correctly adds multiple attributes`(){
        //Given that the user initializes attributes and then adds more attributes
        val attributes = Attributes(
            "color" to "red"
        )
        val addendAttributes = Attributes(
            "src" to "1.jpg",
            "alt" to "image"
        )
        attributes.addAll(addendAttributes)

        //Then the list size should be 3
        Truth.assertThat(attributes).hasSize(3)

        //Then the list has the correct values
        Truth.assertThat(attributes[0]).isEqualTo(Attribute("color", "red"))
        Truth.assertThat(attributes[1]).isEqualTo(Attribute("src", "1.jpg"))
        Truth.assertThat(attributes[2]).isEqualTo(Attribute("alt", "image"))
    }

    @Test
    fun `toString correctly returns empty string when the list is empty`(){
        //Given that the user initializes empty attributes
        val attributes = Attributes()

        //Then toString returns empty string
        Truth.assertThat(attributes.toString()).isEmpty()
    }

    @Test
    fun `toString correctly returns joined string when the list is not empty`(){
        //Given that the user initializes empty attributes
        val attributes = Attributes(
            "src" to "1.jpg",
            "alt" to "image"
        )

        //Then toString returns empty string
        Truth.assertThat(attributes.toString()).isEqualTo(""" src="1.jpg" alt="image"""")
    }
}