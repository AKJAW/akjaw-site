package html

import akjaw.HTML.Attributes
import akjaw.HTML.Tag
import akjaw.Model.Project
import com.beust.klaxon.JsonObject
import html.TagBuilder.createLanguageTagFromJsonObject
import html.helpers.JsonHelpers.parseJsonKey
import html.special_tag.SpecialTagBuilder
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class ProjectBuilder(
    private val projects: List<Project>,
    private val specialTagBuilder: SpecialTagBuilder){

    fun getProjects(tag: Tag) {
        tag.apply {
            projects.map {
                createProject(this, it.projectData)
            }
        }
    }

    private fun createProject(parentTag: Tag, jsonObject: JsonObject): Tag {
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
                    specialTagBuilder.isSpecialTag(it) -> specialTagBuilder.getSpecialTag(this, it, parsed)
                    parsed is String -> getString(it, parsed)
                    parsed is JsonObject -> getNestedTag(it, parsed)
                    else -> throw IllegalStateException(it)
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
            createLanguageTagFromJsonObject(this, key, parsed)
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

}