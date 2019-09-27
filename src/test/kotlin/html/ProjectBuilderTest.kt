package html

import model.Project
import com.beust.klaxon.JsonObject
import com.google.common.truth.Truth
import html.special_tag.SpecialTagBuilder
import org.junit.Test

class ProjectBuilderTest{
    val projects = listOf<Project>(
        Project(createLanguageJsonObject("project1", "first", "pierwszy")),
        Project(createLanguageJsonObject("project2", "second", "drugi"))
    )

    private fun createLanguageJsonObject(name: String, en: String, pl: String): JsonObject {
        return JsonObject(
            mapOf(name to JsonObject(mapOf("en" to en, "pl" to pl)))
        )
    }

    @Test
    fun `getProjects creates a tag for every Project`(){
        val specialTagBuilder = SpecialTagBuilder(listOf())
        val projectBuilder = ProjectBuilder(projects, specialTagBuilder)

        val html = HTMLBuilder.html {  }
        projectBuilder.getProjects(html)

        Truth.assertThat(html.tagList).hasSize(2)
    }


    @Test
    fun `every project tag from getProjects has the project class`(){
        val specialTagBuilder = SpecialTagBuilder(listOf())
        val projectBuilder = ProjectBuilder(projects, specialTagBuilder)

        val html = HTMLBuilder.html {  }
        projectBuilder.getProjects(html)

        Truth.assertThat(html.tagList).hasSize(2)
        Truth.assertThat(html.tagList[0].className).contains("project")
        Truth.assertThat(html.tagList[1].className).contains("project")
    }

}