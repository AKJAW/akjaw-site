package akjaw.html.special_tag

import akjaw.HTML.Attributes
import akjaw.HTML.Tag
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import html.TagBuilder
import html.special_tag.SpecialTag
import java.lang.IllegalStateException

class TechnologyTagsTag: SpecialTag{
    override val signature: String = "technologyTags"

    override fun createTag(tag: Tag, data: Any?, className: String?): Tag {

        return tag.apply{
            tag("div"){
                val classValue = if(className != null){
                    "technology-tags $className"
                } else {
                    "technology-tags"
                }

                + Attributes("class" to classValue)

                val operation: Tag.() -> Unit = {
                    addClass("aaaaaaa")
                }
                TagBuilder.createTagsFromFlatJsonArray(this, data as JsonArray<*>, "div", operation)
            }
        }
    }

}