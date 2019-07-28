package HTML

import akjaw.HTML.SiteBuilder
import akjaw.Model.Project
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.google.common.truth.Truth
import org.junit.Test
import java.io.File
import java.lang.NullPointerException

class SiteBuilderTest{
    private fun createProjectJsonObject(name: String, h1: String): JsonObject{
        return JsonObject(mapOf(name to JsonObject(mapOf("h1" to h1))))
    }

    fun JsonObject.addList(key: String, listItems: List<String>){
        this[key] = JsonArray(listItems)
    }

    @Test
    fun `Always creates a head tag`(){
        val builder = SiteBuilder(listOf())
        val html = builder.html
        Truth.assertThat(html.getByName("head")).isNotNull()
    }

    @Test
    fun `Always creates a body tag`(){
        val builder = SiteBuilder(listOf())
        val html = builder.html
        Truth.assertThat(html.getByName("body")).isNotNull()
    }

    @Test
    fun `Always creates an about me section`(){
        val builder = SiteBuilder(listOf())
        val html = builder.html

        val section = html.getByClass("about-me")
        Truth.assertThat(section).isNotNull()
        Truth.assertThat(section).hasSize(1)
    }

    @Test
    fun `Correctly creates custom list tag`(){
        val jsonObject1 = createProjectJsonObject("project1", "header1")
        (jsonObject1["project1"] as JsonObject).addList(
            "list",
            listOf("first tech", "second tech", "third tech"))

        val projects = listOf(
            Project(jsonObject1)
        )

        val builder = SiteBuilder(projects)
        val html = builder.html

        val ul = html.getByName("ul")!!
        val li = ul[0].getByName("li")!!
        Truth.assertThat(li).hasSize(3)

        Truth.assertThat(li[0].textContent).isEqualTo("first tech")
        Truth.assertThat(li[1].textContent).isEqualTo("second tech")
        Truth.assertThat(li[2].textContent).isEqualTo("third tech")
    }

    @Test
    fun `Correctly creates custom projectTags tag`(){
        val jsonObject1 = createProjectJsonObject("project1", "header1")
        (jsonObject1["project1"] as JsonObject).addList(
            "projectTags",
            listOf("JavaScript", "React", "Firebase"))

        val projects = listOf(
            Project(jsonObject1)
        )

        val builder = SiteBuilder(projects)
        val html = builder.html

        val div = html.getByClass("project-tags")
        div ?: throw NullPointerException("project-tags cant  be null")
        val tags = div[0].tagList
        Truth.assertThat(tags).hasSize(3)
        Truth.assertThat(tags[0].textContent).isEqualTo("JavaScript")
        Truth.assertThat(tags[1].textContent).isEqualTo("React")
        Truth.assertThat(tags[2].textContent).isEqualTo("Firebase")
    }

    @Test
    fun `It correctly saves the html tree to a file`(){
        val path = "src/test/resources/test_file.html"
        File("src/test/resources/test_file.html").delete()

        val builder = SiteBuilder(listOf())
        val html = builder.html

        builder.saveToFile(path)

        Truth.assertThat(File(path).exists()).isTrue()
        val fileContent = File(path).readText()
        Truth.assertThat(fileContent.contains("<head>")).isTrue()
        Truth.assertThat(fileContent.contains("<body>")).isTrue()

    }
}