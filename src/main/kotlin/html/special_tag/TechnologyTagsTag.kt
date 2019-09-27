package html.special_tag

import html.Attributes
import html.Tag
import com.beust.klaxon.JsonArray
import html.TagBuilder

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

                TagBuilder.createTagsFromFlatJsonArray(this, data as JsonArray<*>, "div")
            }
        }
    }

}