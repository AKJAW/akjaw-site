package akjaw.HTML

import akjaw.Model.Project
import akjaw.Repository.JsonRepository
import com.beust.klaxon.JsonArray
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
                when{
                    isSpecialTag(it) -> getSpecialTag(it, parsed)
                    parsed is String -> getString(it, parsed)
                    parsed is JsonObject -> getNestedTag(it, parsed)
                    else -> {
                        val s = 's'
                        throw IllegalStateException()
                    }
                }
        }
        return this
    }

    private fun isSpecialTag(name: String): Boolean {
        return (
                name == "list"
                )
    }


    private fun Tag.getSpecialTag(key: String, parsed: Any?): Tag {
        val (name, className) = parseJsonKey(key)

        return when(name){
            "list" -> getListTag(name, className, parsed as JsonArray<*>)
            else -> throw IllegalStateException("tagName $name doesnt exists")
        }
    }

    private fun Tag.getListTag(name: String, className: String?, listItems: JsonArray<*>): Tag {
        return tag("ul"){
            listItems.forEach {
                tag("li"){
                    + (it as String)
                }
            }
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

    private fun Tag.getNestedTag(key: String, parsed: JsonObject): Tag {
        val (tagName, tagClass) = parseJsonKey(key)
        return tag(tagName) {
            if(tagClass != null){
                + Attributes("class" to tagClass)
            }
            getTag(parsed)
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
    val projects = JsonRepository("data/projects.json").projects
    val siteBuilder = SiteBuilder(projects)
    val html = siteBuilder.build()
    print(html)
    val s = 's'
}