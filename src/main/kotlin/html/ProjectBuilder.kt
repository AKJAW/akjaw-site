package html

import akjaw.HTML.Attributes
import akjaw.HTML.Tag
import akjaw.HTML.TagBuilder
import html.helpers.parseJsonKey
import html.special_tag.SpecialTagBuilder
import com.beust.klaxon.JsonObject
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

object ProjectBuilder{

    fun createProject(parentTag: Tag, jsonObject: JsonObject): Tag {
        return parentTag.apply {
            tag("div"){
                addClass("project")
                getTag(jsonObject)
            }
        }
    }

    private fun Tag.getTag(jsonObject: JsonObject): Tag {
        if(jsonObject.keys.size == 0){
            throw IllegalArgumentException("empty JsonObject")
        }

            jsonObject.keys.forEach {
                val parsed = jsonObject[it]
                when{
                    SpecialTagBuilder.isSpecialTag(it) -> SpecialTagBuilder.getSpecialTag(this, it, parsed)
                    parsed is String -> getString(it, parsed)
                    parsed is JsonObject -> getNestedTag(it, parsed)
                    else -> throw IllegalStateException()
                }
            }

        return this
    }

    private fun Tag.getString(key: String, value: String): Tag {
        val (tagName, tagClass) = parseJsonKey(key)
        return tag(tagName){
            + value
            if(tagClass != null){
                + Attributes("class" to tagClass)
            }

        }
    }


    private fun Tag.getNestedTag(key: String, parsed: JsonObject) {
        if(isNestedTagALanguageContainer(parsed)){
            createLanguageTagFromJsonObject(key, parsed)
        } else {
            val (tagName, tagClass) = parseJsonKey(key)
            tag(tagName) {
                if(tagClass != null){
                    + Attributes("class" to tagClass)
                }
                getTag(parsed)
            }
        }
    }

    private fun isNestedTagALanguageContainer(parsed: JsonObject): Boolean{
        return if(parsed.keys.size == 2){
            val first = parsed.keys.first()

            return first == "en" || first == "pl"
        } else {
            false
        }
    }

    fun Tag.createLanguageTagFromJsonObject(key: String, parsed: JsonObject) {
        val (tagName, tagClass) = parseJsonKey(key)

        TagBuilder.createTagWithLanguages(
            this,
            tagName,
            parsed.getValue("en") as String,
            parsed.getValue("pl") as String,
            tagClass

        )

    }

}