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

                createTechnologyTags(data)
            }
        }
    }

    private fun Tag.createTechnologyTags(data: Any?){
        val technologyTags = data as JsonArray<*>

        technologyTags
            .forEach {
                if(it is JsonObject){
                    TagBuilder.createLanguageTagFromJsonObject(this, "div", it)
                } else {
                    getListItem(it)
                }
            }
    }


    private fun Tag.getListItem(item: Any?){
        tag("div") {
            if (item is String) {
                + item
            }
            else {
                throw IllegalStateException("List item has incorrect type")
            }
        }
    }
}