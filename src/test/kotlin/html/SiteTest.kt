package html

import akjaw.Model.Project
import akjaw.Repository.JsonRepository
import akjaw.html.special_tag.TechnologyTagsTag
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.google.common.truth.Truth
import html.special_tag.ListTag
import html.special_tag.SpecialTag
import html.special_tag.SpecialTagBuilder
import org.junit.Test
import java.io.File

class SiteTest{
    val specialTags = listOf<SpecialTag>(
        ListTag(),
        TechnologyTagsTag()
    )
    val specialTagBuilder = SpecialTagBuilder(specialTags)

    val projects = listOf<Project>(
        Project(createLanguageJsonObject("project1", "first", "pierwszy")),
        Project(createLanguageJsonObject("project2", "second", "drugi"))
    )
    val projectBuilder = ProjectBuilder(projects, specialTagBuilder)


    private fun createLanguageJsonObject(name: String, en: String, pl: String): JsonObject {
        return JsonObject(
            mapOf(name to JsonObject(mapOf("en" to en, "pl" to pl)))
        )
    }

    @Test
    fun `Always creates a head tag`(){
        val siteBuilder = Site(projectBuilder)

        val html = siteBuilder.html
        Truth.assertThat(html.getByName("head")).isNotNull()
    }

    @Test
    fun `Always creates a body tag`(){
        val siteBuilder = Site(projectBuilder)

        val html = siteBuilder.html
        Truth.assertThat(html.getByName("body")).isNotNull()
    }

    @Test
    fun `Always creates an about me section`(){
        val siteBuilder = Site(projectBuilder)
        val html = siteBuilder.html

        val section = html.getByClass("about-me")
        Truth.assertThat(section).isNotNull()
        Truth.assertThat(section).hasSize(1)
    }

    @Test
    fun `It correctly saves the html tree to a file`(){
        val path = "src/test/resources/test_file.html"
        File("src/test/resources/test_file.html").delete()

        val siteBuilder = Site(projectBuilder)
        val html = siteBuilder.html

        siteBuilder.saveToFile(path)

        Truth.assertThat(File(path).exists()).isTrue()
        val fileContent = File(path).readText()
        Truth.assertThat(fileContent.contains("<head>")).isTrue()
        Truth.assertThat(fileContent.contains("<body>")).isTrue()

    }
}