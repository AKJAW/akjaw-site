package html.special_tag

import html.Attributes
import html.Tag
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.sun.javaws.exceptions.InvalidArgumentException
import html.TagBuilder

class IconTag: SpecialTag {
    override val signature: String = "icon"

    override fun createTag(tag: Tag, data: Any?, className: String?): Tag {

        return tag.apply{
            tag("img"){
                val src = data as? String ?: throw IllegalArgumentException("icon value is not a string")

                + Attributes(
                    "class" to "project-icon ${className.orEmpty()}",
                    "src" to src
                    )

            }
        }
    }
}