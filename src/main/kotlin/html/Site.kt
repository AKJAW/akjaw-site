package html

import com.beust.klaxon.JsonObject
import html.special_tag.*
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
        tag("head") {
            tag("title") {
                + "Aleksander Jaworski"
            }

            tag("link") {
                +Attributes("rel" to "stylesheet", "href" to "style.css")
            }
            tag("script") {
                +Attributes("src" to "lory.min.js", "type" to "text/javascript")
            }
            tag("script") {
                +Attributes("src" to "main.js", "type" to "text/javascript")
            }
            tag("meta") {
                +Attributes("name" to "viewport", "content" to "width=device-width,initial-scale=1,shrink-to-fit=no")
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

                projectBuilder.getProjects(this)
            }
        }
    }

    private fun Tag.createHeader() {
        tag("div"){
            +Attributes("class" to "header")
            tag("div"){
                +Attributes("class" to "site-break")
                tag("h3"){
                    + "AKJAW"
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
                "span",
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
                "div",
                "My name is Aleksander Jaworski. Currently im am studying applied computer science at the University of Silesia. I am working as a remote developer for 4 online stores and Bubble quiz games. I have finished many projects, mostly web ones, but I have made some desktop application. Also I am working on my own Android app. This web page is a portfolio for these projects.",
                "Nazywam się Aleksander Jaworski. Obecnie jestem studentem informatyki stosowanej na Uniwersytecie Śląskim. Pracuję jako zdalny deweloper dla 4 sklepów internetowych oraz Bubble quiz games. Zakończyłem wiele projektów, głownie internetowych, ale stworzyłem też parę aplikacji desktopowych. Dodatkowo pracuje nad swoją własną aplikacją na systemy Android. Ta strona jest moim portfolio dla tych projektów."
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
        LinkTag()
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
