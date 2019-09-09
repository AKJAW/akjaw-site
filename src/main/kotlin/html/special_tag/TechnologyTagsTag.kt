package akjaw.html.special_tag

import akjaw.HTML.Attributes
import akjaw.HTML.Tag
import com.beust.klaxon.JsonArray
import html.special_tag.SpecialTag

class TechnologyTagsTag: SpecialTag{
    override val signature: String = "technologyTags"

    override fun createTag(tag: Tag, className: String?, data: Any?): Tag {

        return tag.apply{
            tag("div"){
                val classValue = if(className != null){
                    "technology-tags $className"
                } else {
                    "technology-tags"
                }

                + Attributes("class" to classValue)

                createTechnologyTags(data)
            }
        }
    }

    private fun Tag.createTechnologyTags(data: Any?){
        val technologyTags = data as JsonArray<*>

        technologyTags
            .map { it as String }
            .forEach { tagName ->
                tag("div"){
                    + tagName
                }
            }
    }


}