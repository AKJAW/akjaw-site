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
            tag("meta"){
                + Attributes("charset" to "UTF-8")
            }
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
                + Attributes("src" to "main.js", "type" to "text/javascript")
            }
        }
    }

    private fun Tag.createBody(){
        tag("body"){
            createHeader()
            tag("div"){
                + Attributes("class" to "content site-break")
                createAboutMeSection()
                projects.map {
                    tag("div"){
                        addClass("project")
                        getTag(it.projectData)
                    }

                }
            }
        }
    }

    private fun Tag.createHeader() {
        tag("div"){
            + Attributes("class" to "header")
            tag("div"){
                + Attributes("class" to "site-break")
                tag("h3"){
                    + "AKJAW"
                }

                createLanguageIcons()

                tag("div"){
                    + Attributes("class" to "icon-container")
                    createHeaderIcon(
                        "assets/GitHub-Mark-32px.png",
                        "https://github.com/AKJAW",
                        "github-icon")
                }
            }
        }
    }

    private fun Tag.createLanguageIcons(){
        tag("div"){
            + Attributes("class" to "language-container")

            tag("div"){
                + Attributes("id" to "en-button", "class" to "language-button active")
            }

            tag("div"){
                + Attributes("id" to "pl-button", "class" to "language-button")
            }
        }
    }

    private fun Tag.createHeaderIcon(iconPath: String, link: String, iconClassName: String){
        tag("a"){
            + Attributes(
                "class" to "icon $iconClassName",
                "href" to link,
                "target" to "_blank")
            tag("img"){
                + Attributes("src" to iconPath)
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

    private fun Tag.createTagWithLanguages(//TODO ma tworzyć dwa tagi rodzica z językiem, nie dwa divy w środku
        tagName: String,
        englishTextContent: String,
        polishTextContent: String,
        classes: String? = null){

        val classValue = if(classes == null){
            ""
        } else {
            " $classes"
        }

        tag(tagName){
            + Attributes("class" to "en$classValue")
            + englishTextContent
        }

        tag(tagName){
            + Attributes("class" to "pl none $classValue")
            + polishTextContent
        }
    }

    private fun Tag.getTag(jsonObject: JsonObject): Tag {
        if(jsonObject.keys.size == 0){
            val s = 's'
        }
        jsonObject.keys.forEach {
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
        return when(name){
            "list" -> true
            "projectTags" -> true
            else -> false
        }
    }


    private fun Tag.getSpecialTag(key: String, parsed: Any?) {
        val (name, className) = parseJsonKey(key)

        when(name){
            "list" -> createList(className, parsed as JsonArray<*>)
            "projectTags" -> createProjectTags(className, parsed as JsonArray<*>)
            else -> throw IllegalStateException("tagName $name doesnt exists")
        }
    }

    private fun Tag.createList(className: String?, listItems: JsonArray<*>) {
        tag("ul"){
            if (className != null){
                + Attributes("class" to className)
            }
            listItems.forEach {
                getListItem(it)

            }
        }
    }

    private fun Tag.getListItem(item: Any?){
        tag("li") {
            if (item is String) {
                + item
            } else if (item is JsonObject) {
                item.forEach {
                    getString(it.key, it.value as String)
                }
            } else {
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

    private fun Tag.getString(key: String, value: String): Tag {
        return getSimpleString(key, value)
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

    private fun Tag.getNestedTag(key: String, parsed: JsonObject) {
        if(isNestedTagALanguageContainer(parsed)){
            createLanguageTag(key, parsed)
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

    private fun isNestedTagALanguageContainer(parsed: JsonObject): Boolean{
        return if(parsed.keys.size == 2){
            val first = parsed.keys.first()

            return first == "en" || first == "pl"
        } else {
            false
        }
    }

    private fun Tag.createLanguageTag(key: String, parsed: JsonObject) {
        val (tagName, tagClass) = parseJsonKey(key)

        parsed.keys.map {
            tag(tagName) {
                val classValue = if(tagClass != null){
                    "$it $tagClass"
                } else {
                    it
                }

                + Attributes("class" to classValue)

                if(it == "pl"){//TODO wszędzie
                    addClass("none")
                }


                val text = parsed[it]
                if(text is String){
                    + text
                }
            }

//            this.append(languageTag)
        }
    }

    fun saveToFile(path: String) {
        val file = File(path)
        file.createNewFile()
        file.writeText(html.toString())
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