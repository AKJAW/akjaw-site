package akjaw.HTML

import akjaw.Attributes
import akjaw.HTMLBuilder
import akjaw.Model.Project
import akjaw.Repository.Repository
import akjaw.Tag
import com.beust.klaxon.JsonObject

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
                getTag(it.projectData)

            }
        }
    }

    private fun Tag.getTag(jsonObject: JsonObject): Tag{
        if(jsonObject.keys.size == 0){
            val s = 's'
        }
        if(jsonObject.keys.size == 1){
            val name = jsonObject.keys.first().toString()
            val (tagName, tagIndentificator) = name.split("-")

            val tagClass = when{
                tagIndentificator.toIntOrNull() == null -> tagIndentificator
                else -> ""
            }
            return tag(name)
        }
        jsonObject.keys.map {
            getTag(jsonObject.obj(it)!!)
        }
    }

}

fun main(args: Array<String>){
    val projects = Repository("data/projects.json").getProjects()
    val siteBuilder = SiteBuilder(projects)
    val html = siteBuilder.build()
    val s = 's'
}