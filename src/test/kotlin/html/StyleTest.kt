package html

import com.google.common.truth.Truth
import org.junit.Test

class StyleTest{
    @Test
    fun `toString correctly formats style with a single value`(){
        //Given that the user initializes a style with one value
        val style = Style("color" to "red")

        //Then toString correctly formats the style
        Truth.assertThat(style.toString()).isEqualTo("color:red")
    }

    @Test
    fun `toString correctly formats style with mutliple values`(){
        //Given that the user initializes a style with one value
        val style = Style(
            "color" to "red",
            "background" to "black",
            "margin-top" to "5px"
        )

        //Then toString correctly formats the style
        Truth.assertThat(style.toString()).isEqualTo("color:red; background:black; margin-top:5px")
    }

    @Test
    fun `toString ignores uppercase letters`(){
        //Given that the user initializes a style with one value
        val style = Style("Color" to "Red")

        //Then toString correctly formats the style
        Truth.assertThat(style.toString()).isEqualTo("color:red")
    }
}