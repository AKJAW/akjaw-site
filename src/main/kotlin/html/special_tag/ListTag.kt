package html.special_tag

import akjaw.HTML.Attributes
import akjaw.HTML.Tag
import akjaw.HTML.TagBuilder.createLanguageTagFromJsonObject
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import java.lang.IllegalStateException

class ListTag: SpecialTag {
    override val signature: String = "list"

    override fun createTag(tag: Tag, className: String?, data: Any?): Tag {
        val listItems = data as JsonArray<*>

        return tag.apply{
            if (className != null){
                + Attributes("class" to className)
            }
            listItems.forEach {
                if(it is JsonObject){
                    createLanguageTagFromJsonObject(this, "li", it)
                } else {
                    getListItem(it)
                }
            }
        }
    }

    private fun Tag.getListItem(item: Any?){
        tag("li") {
            if (item is String) {
                + item
            }
            else {
                throw IllegalStateException("List item has incorrect type")
            }
        }
    }
}