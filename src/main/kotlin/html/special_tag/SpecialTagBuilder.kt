package html.special_tag

import akjaw.HTML.Attributes
import akjaw.HTML.Tag
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import html.helpers.JsonHelpers.parseJsonKey
import java.lang.IllegalStateException

class SpecialTagBuilder(specialTags: List<SpecialTag>){
    private val specialTagMap = specialTags.map {
        it.signature to it
    }.toMap()

    fun isSpecialTag(name: String): Boolean {
        return specialTagMap.keys.any { it == name }
    }

    fun getSpecialTag(tag: Tag, key: String, parsed: Any?) {
        val (name, className) = parseJsonKey(key)

        specialTagMap
            .getValue(name)
            .createTag(tag, className, parsed)
    }

}