package html.special_tag

import html.helpers.parseJsonKey
import akjaw.HTML.Attributes
import akjaw.HTML.Tag
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import html.ProjectBuilder.createLanguageTagFromJsonObject
import java.lang.IllegalStateException

object SpecialTagBuilder{
    enum class SpecialTag(val signature: String) {
        LIST("list"),
        PROJECT_TAG("projectTags");

        companion object {
            fun isSpecialTag(name: String): Boolean{
                return values().any {
                    name.startsWith(it.signature)
                }
            }
        }
    }

    fun isSpecialTag(name: String): Boolean {
        return SpecialTagBuilder.SpecialTag.isSpecialTag(name)
    }

    fun getSpecialTag(tag: Tag, key: String, parsed: Any?) {
        val (name, className) = parseJsonKey(key)

        tag.apply{
            when{
                name.startsWith(SpecialTagBuilder.SpecialTag.LIST.signature) -> {
                    createList(className, parsed as JsonArray<*>)
                }
                name.startsWith(SpecialTagBuilder.SpecialTag.PROJECT_TAG.signature) -> {
                    createProjectTags(className, parsed as JsonArray<*>)
                }
                else -> throw IllegalStateException("tagName $name doesnt exists")
            }
        }
    }

    private fun Tag.createList(className: String?, listItems: JsonArray<*>) {
        tag("ul"){
            if (className != null){
                + Attributes("class" to className)
            }
            listItems.forEach {
                if(it is JsonObject){
                    createLanguageTagFromJsonObject("li", it)
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

    private fun Tag.createProjectTags(className: String?, projectTags: JsonArray<*>){
        tag("div"){
            val classValue = if(className != null){
                "project-tags $className"
            } else {
                "project-tags"
            }

            + Attributes("class" to classValue)

            projectTags
                .map { it as String }
                .forEach { tagName ->
                    tag("div"){
                        + tagName
                    }
                }
        }
    }
}