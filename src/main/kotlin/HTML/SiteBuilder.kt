package akjaw.HTML

import akjaw.Attributes
import akjaw.HTMLBuilder
import akjaw.Model.Project
import akjaw.Repository.Repository
import akjaw.Style
import akjaw.Tag
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
                val s = 's'
                tag("div"){
                    getTag(it.projectData)
                }

            }
        }
    }

    private fun Tag.getTag(jsonObject: JsonObject): Tag{
        if(jsonObject.keys.size == 0){
            val s = 's'
        }
        jsonObject.keys.map {

            val parsed = jsonObject[it]
            when(parsed){
                is String -> {
                    if(it == "pl" || it == "en"){
                        getLanguageString(it, parsed)
                    } else {
                        getString(it, parsed)
                    }
                }
                is JsonObject -> getTag(parsed)
                else -> {
                    val s = 's'
                    throw IllegalStateException()
                }
            }
        }
        return this
    }

    private fun Tag.getLanguageString(languageKey: String, value: String): Tag{
        return tag("div"){
            //            if(tagClass != null){
//                + Style("class" to tagClass)
//            }
            + Attributes("class" to languageKey)
        }
    }

    private fun Tag.getString(key: String, value: String): Tag{
        val (tagName, tagClass) = parseJsonKey(key)
        return tag(tagName){
            if(tagClass != null){
                + Attributes("class" to tagClass)
            }
        }
    }

    private fun parseJsonKey(key: String): Pair<String, String?> {
        val splitted = key.split("-")
        if(splitted.size == 1){
            return splitted[0] to null
        }
        val tagName = splitted[0]
        val tagClass: String = splitted[1]

        return if(tagClass.toIntOrNull() != null){
            tagName to null
        } else {
            tagName to tagClass
        }

    }

}

fun main(args: Array<String>){
    val projects = Repository("data/projects.json").getProjects()
    val siteBuilder = SiteBuilder(projects)
    val html = siteBuilder.build()
    print(html)
    val s = 's'
}