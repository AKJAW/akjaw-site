package html

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import html.special_tag.*
import model.Project
import repository.JsonRepository
import java.io.File

class Site(private val projectBuilder: ProjectBuilder){
    val html: Tag

    init {
        html = HTMLBuilder.html {
            tag("meta"){
                +Attributes("charset" to "UTF-8")
            }
            createHead()
            createBody()
        }
    }

    private fun Tag.createHead() {
        val release = "3"
        tag("head") {
            tag("title") {
                + "Aleksander Jaworski"
            }
            tag("link") {
                +Attributes("rel" to "icon", "type" to "image/png", "href" to "/favicon-32x32.png")
            }
            tag("link") {
                +Attributes("rel" to "icon", "type" to "image/png", "href" to "/favicon-16x16.png")
            }
            tag("link") {
                +Attributes("href" to "style.css?v=$release", "rel" to "stylesheet")
            }
            tag("script") {
                +Attributes("src" to "lory.min.js", "type" to "text/javascript")
            }
            tag("script") {
                +Attributes("src" to "main.js?v=$release", "type" to "text/javascript")
            }
            tag("meta") {
                +Attributes("name" to "viewport", "content" to "width=device-width, initial-scale=1.0")
            }
            tag("meta") {
                +Attributes("name" to "description", "content" to "My personal website and a portfolio for my work")
            }
        }
    }

    private fun Tag.createBody(){
        tag("body"){
            createHeader()
            tag("div"){
                +Attributes("class" to "content site-break")
                createUnderConstructionText()
                createAboutMeSection()
                createSocialSection()
                createTagSection(projectBuilder.projects)

                projectBuilder.createProjects(this)
            }
        }
    }

    private fun Tag.createHeader() {
        tag("div"){
            +Attributes("class" to "header")
            tag("div"){
                +Attributes("class" to "site-break")
                tag("img"){
                    + Attributes(
                        "class" to "logo",
                        "src" to "assets/logo-white.png")
                }

                createLanguageIcons()

//                tag("div"){
//                    +Attributes("class" to "icon-container")
//                    createHeaderIcon(
//                        "assets/GitHub-Mark-32px.png",
//                        "https://github.com/AKJAW",
//                        "github-icon")
//                }
            }
        }
    }

    private fun Tag.createLanguageIcons(){
        tag("div"){
            +Attributes("class" to "language-container")

            tag("div"){
                +Attributes("id" to "en-button", "class" to "language-button active")
            }

            tag("div"){
                +Attributes("id" to "pl-button", "class" to "language-button")
            }
        }
    }

    private fun Tag.createUnderConstructionText(){
        tag("div"){
            +Attributes("class" to "under-construction")

            TagBuilder.createTagWithLanguages(
                this,
                "div",
                "Site is still under construction, best viewed on Desktop",
                "Strona jest w trakcie przygotowania, najlepiej przeglądać na Desktop")
        }
    }

    private fun Tag.createAboutMeSection() {
        tag("div"){
            +Attributes("class" to "content-out-of-box")
            TagBuilder.createTagWithLanguages(
                this,
                "h2",
                "About me",
                "O mnie")

            TagBuilder.createTagWithLanguages(
                this,
                "p",
                "My name is Aleksander Jaworski, currently I am studying applied computer science at the University of Silesia. I started programming in the middle of 2016 and have been doing it almost everyday since. My work experience includes several web and desktop applications for three online stores and Bubble quiz games. I've finished some personal projects on the side, but they were created mostly for learning purposes. My biggest personal project is my Android app Timi.",
                "Nazywam się Aleksander Jaworski, obecnie jestem studentem informatyki stosowanej na Uniwersytecie Śląskim. Zacząłem programować w połowie 2016 i od tego czasu robię to prawie codziennie. Jako programista stworzyłem parę aplikacji internetowych oraz komputerowych dla trzech sklepów internetowych oraz Bubble quiz games. Skończyłem trochę osobistych projektów, ale były one stworzone głównie w celu edukacyjnych. Moim największym osobistym projektem jest aplikacja na Android Timi."
            )

            TagBuilder.createTagWithLanguages(
                this,
                "p",
                "This web page is a portfolio for all my notable projects.",
                "Ta strona jest portfolio dla większości moich projektów."

            )
        }
    }

    private fun Tag.createSocialSection(){
        tag("div"){
            + Attributes("class" to "content-out-of-box")

            TagBuilder.createTagWithLanguages(this,
                "h3",
                "Social links",
                "Linki social")

            createSocialLink(
                "AKJAW",
                "https://github.com/AKJAW",
                "assets/GitHub-Mark-32px.png"
            )

            createSocialLink(
                "Aleksander Jaworski",
                "https://www.linkedin.com/in/akjaw/",
                "assets/linked-in.png"
            )
        }
    }

    private fun Tag.createSocialLink(text: String, href: String, icon: String){
        val textContent = "<img src=$icon /> $text"

        val jsonObject = JsonObject(mapOf("text-en" to textContent, "href" to href))

        tag("p"){
            LinkTag()
                .createTag(this, jsonObject, "social-link")
        }
    }

    private fun Tag.createTagSection(projects: List<Project>){
        val tags: List<List<String>?> = projects.map {
            (it.projectData["technologyTags"] as? JsonArray<String>)?.value
        }

        val tagSet: Set<String> = tags
            .filterNotNull()
            .flatten()
            .toSet()

        tag("div") {
            +Attributes("class" to "content-out-of-box")
            TagBuilder.createTagWithLanguages(
                this,
                "h3",
                "Available tags",
                "Dostępne tagi"
            )

            tag("div") {
                +Attributes("class" to "technology-tags", "id" to "available")
                tagSet.map {
                    tag("div") {
                        +it
                    }
                }
            }

            TagBuilder.createTagWithLanguages(
                this,
                "h3",
                "Selected tags",
                "Wybrane tagi"
            )

            tag("div") {
                +Attributes("class" to "technology-tags", "id" to "selected")
            }
        }
    }

    fun saveToFile(path: String) {
        val file = File(path)
        file.createNewFile()
        file.writeText(html.toString())
    }

}

fun main(){
    val specialTags = listOf<SpecialTag>(
        ListTag(),
        TechnologyTagsTag(),
        GalleryTag(),
        LinkTag(),
        IconTag(),
        ImageLinkTag()
    )
    val specialTagBuilder = SpecialTagBuilder(specialTags)

    val projects = JsonRepository("data/projects.json").projects
    val projectBuilder = ProjectBuilder(projects, specialTagBuilder)

    val siteBuilder = Site(projectBuilder)

    val html = siteBuilder.html
    siteBuilder.saveToFile("build/index.html")
    print(html)
    val s = 's'

}
