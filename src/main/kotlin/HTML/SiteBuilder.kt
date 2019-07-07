package akjaw.HTML

import akjaw.Model.Project
import akjaw.Repository.JsonRepository
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import java.io.File
import java.lang.IllegalStateException

class SiteBuilder(private val projects: List<Project>){
    val html: Tag

    init {
        html = HTMLBuilder.html {
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
            createAboutMeSection()
            projects.map {
                tag("div"){
                    getTag(it.projectData)
                }

            }
        }
    }

    private fun Tag.createAboutMeSection() {
        tag("div"){
            + Attributes("class" to "about-me")
            createTagWithLanguages(
                "h2",
                "About me",
                "O mnie")

            createTagWithLanguages(
                "div",
                "My name is Aleksander Jaworski, im am studying Applied computer science. I am working as a remote developer for 4 online stores, and for Bubble quiz games. I have finished many projects, mostly web ones, but i have made some desktop application and also i am working on my own Android app. This web page is a portfolio for these projects",
                "Nazywam się Aleksander Jaworski, jestem studentem informatyki stosowanej. Pracuję jako zdalnym deweloper dla 4 sklepów internetowych, oraz dla Bubble quiz games. Zakończyłem wiele projektów, głownie internetowych, ale stworzyłem też pare aplikacji desktopowych, oraz aktualnie pracuje nad swoją własną aplikacją na systemy Android. Ta strona jest moim portfolio w którym opisane są te projekty"
                )
        }
    }

    private fun Tag.createTagWithLanguages(
        tagName: String,
        englishTextContent: String,
        polishTextContent: String,
        classes: String? = null){

        tag(tagName){
            if(classes != null){
                + Attributes("class" to "en $classes")
            }
            + englishTextContent
        }

        tag(tagName){
            if(classes != null){
                + Attributes("class" to "en $classes")
            }
            + polishTextContent
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
                    else -> throw IllegalStateException()
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

    fun saveToFile(path: String) {
        val file = File(path)
        file.createNewFile()
        file.writeText(html.toString(), Charsets.UTF_16)
    }

}

fun main(){
    val projects = JsonRepository("data/projects.json").projects
    val siteBuilder = SiteBuilder(projects)
    val html = siteBuilder.html
    siteBuilder.saveToFile("build/index.html")
    print(html)
    val s = 's'
}