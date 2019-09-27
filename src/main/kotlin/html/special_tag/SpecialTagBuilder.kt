package html.special_tag

import html.Tag
import html.helpers.JsonHelpers.parseJsonKey

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
            .createTag(tag, parsed, className)
    }

}