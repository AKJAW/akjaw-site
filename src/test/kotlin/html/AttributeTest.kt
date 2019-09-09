package html

import akjaw.HTML.Attribute
import com.google.common.truth.Truth
import org.junit.Test

class AttributeTest{

    @Test
    fun `toString correctly formats values`(){
        //Given that the user creates an Attribute
        val attribute = Attribute("src", "1.jpg")

        //Then toString should correctly formats the values
        Truth.assertThat(attribute.toString()).isEqualTo("""src="1.jpg"""")
    }
}