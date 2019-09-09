package html.special_tag

import akjaw.HTML.Attributes
import akjaw.HTML.Tag
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import html.TagBuilder.createLanguageTagFromJsonObject
import java.lang.IllegalStateException

class ListTag: SpecialTag {
    override val signature: String = "list"

    override fun createTag(tag: Tag, data: Any?, className: String?): Tag {

        return tag.apply{
            tag("ul"){
                if (className != null){
                    + Attributes("class" to className)
                }
                addListChilder(data)
            }
        }
    }

    private fun Tag.addListChilder(data: Any?){
        val listItems = data as JsonArray<*>

        listItems.forEach {
            if(it is JsonObject){
                createLanguageTagFromJsonObject(this, "li", it)
            } else {
                getListItem(it)
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