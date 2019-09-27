package html.special_tag

import html.Tag

interface SpecialTag{
    val signature: String

    fun isSpecialTag(name: String): Boolean{
        val split = name.split("-")
        return if(split.isEmpty()){
            name == signature
        } else {
            split[0] == signature
        }
    }
    fun createTag(tag: Tag, data: Any?, className: String? = null): Tag
}