package akjaw.HTML

import akjaw.Model.Project
import akjaw.Repository.Repository
import com.beust.klaxon.JsonObject
import java.lang.IllegalStateException

class SiteBuilder(private val projects: List<Project>){

    fun build(): Tag {
        return HTMLBuilder.html {
            createHead()
            createBody()
        }
    }

    private fun Tag.createHead() {
        tag("head") {
            tag("link") {
                + Attributes("rel" to "stylesheet", "href" to "style.css")
            }
            tag("script") {
                + Attributes("src" to "script.js")
            }
        }
    }

    private fun Tag.createBody(){
        tag("body"){
            projects.map {
                tag("div"){
                    getTag(it.projectData)
                }

            }
        }
    }

    private fun Tag.getTag(jsonObject: JsonObject): Tag {
        if(jsonObject.keys.size == 0){
            val s = 's'
        }
        jsonObject.keys.map {
                val parsed = jsonObject[it]
                when(parsed){
                    is String -> getString(it, parsed)
                    is JsonObject -> getNestedTag(it, parsed)
                    else -> {
                        val s = 's'
                        throw IllegalStateException()
                    }
                }
        }
        return this
    }

    private fun Tag.getNestedTag(key: String, parsed: JsonObject): Tag {
        val (tagName, tagClass) = parseJsonKey(key)
        return tag(tagName) {
            if(tagClass != null){
                + Attributes("class" to tagClass)
            }
            getTag(parsed)
        }
    }

    private fun Tag.getString(key: String, value: String): Tag {
        return if(key == "pl" || key == "en"){
            getLanguageString(key, value)
        } else {
            getSimpleString(key, value)
        }
    }

    private fun Tag.getLanguageString(languageKey: String, value: String): Tag {
        return tag("div"){
            + value
            + Attributes("class" to languageKey)
        }
    }

    private fun Tag.getSimpleString(key: String, value: String): Tag {
        val (tagName, tagClass) = parseJsonKey(key)
        return tag(tagName){
            + value
            if(tagClass != null){
                + Attributes("class" to tagClass)
            }

        }
    }

    private fun parseJsonKey(key: String): Pair<String, String?> {
        val split = key.split("-")
        if(split.size == 1){
            return split[0] to null
        }
        val (tagName, tagClass) = split

        return if(tagClass.toIntOrNull() != null){
            tagName to null
        } else {
            tagName to tagClass
        }

    }

}

fun main(){
    val projects = Repository("data/projects.json").getProjects()
    val siteBuilder = SiteBuilder(projects)
    val html = siteBuilder.build()
    print(html)
    val s = 's'
}