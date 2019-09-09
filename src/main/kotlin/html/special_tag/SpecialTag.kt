package html.special_tag

import akjaw.HTML.Tag
import com.beust.klaxon.JsonArray

interface SpecialTag{
    val signature: String

    fun isSpecialTag(name: String): Boolean = name.startsWith(signature)
    fun createTag(tag: Tag, className: String?, data: Any?): Tag
}