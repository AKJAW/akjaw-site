package html.special_tag

import akjaw.HTML.Attributes
import akjaw.HTML.Tag
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import html.TagBuilder
import java.lang.IllegalStateException

class ListTag: SpecialTag {
    override val signature: String = "list"

    override fun createTag(tag: Tag, data: Any?, className: String?): Tag {

        return tag.apply{
            tag("ul"){
                if (className != null){
                    + Attributes("class" to className)
                }

                TagBuilder.createTagsFromFlatJsonArray(this, data as JsonArray<*>, "li")
            }
        }
    }
}