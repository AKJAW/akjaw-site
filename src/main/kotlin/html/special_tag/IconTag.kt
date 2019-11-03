package html.special_tag

import html.Attributes
import html.Tag

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