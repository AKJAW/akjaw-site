package html.special_tag

import akjaw.html.special_tag.TechnologyTagsTag
import com.google.common.truth.Truth
import org.junit.Test

class SpecialTagBuilderTest{

    @Test
    fun `isSpecialTag check if any of the SpecialTag signatures are the same as the one passed in`(){
        val specialTags = listOf<SpecialTag>(
            ListTag(),
            TechnologyTagsTag()
        )
        val specialTagBuilder = SpecialTagBuilder(specialTags)

        Truth.assertThat(specialTagBuilder.isSpecialTag("list")).isTrue()
        Truth.assertThat(specialTagBuilder.isSpecialTag("lists")).isFalse()
        Truth.assertThat(specialTagBuilder.isSpecialTag("technologyTags")).isTrue()
        Truth.assertThat(specialTagBuilder.isSpecialTag("TechnologyTags")).isFalse()
    }
}