package html.special_tag

import html.Attributes
import html.Tag
import com.beust.klaxon.JsonArray
import html.TagBuilder

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