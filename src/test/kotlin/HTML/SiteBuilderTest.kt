package HTML

import akjaw.HTML.SiteBuilder
import akjaw.Model.Project
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.google.common.truth.Truth
import org.junit.Test

class SiteBuilderTest{
    val projects: List<Project>
    init {
        val jsonObject1 = createProjectJsonObject("project1", "header1")
        (jsonObject1["project1"] as JsonObject).addList(
            listOf("first tech", "second tech", "third tech"))

        projects = listOf(
            Project(jsonObject1)
        )
    }

    private fun createProjectJsonObject(name: String, h1: String): JsonObject{
        return JsonObject(mapOf(name to JsonObject(mapOf("h1" to h1))))
    }

    fun JsonObject.addList(listItems: List<String>){
        this["list"] = JsonArray(listItems)
    }

    @Test
    fun `always creates a head tag`(){
        val builder = SiteBuilder(listOf())
        val html = builder.build()
        Truth.assertThat(html.getByName("head")).isNotNull()
    }

    @Test
    fun `always creates a body tag`(){
        val builder = SiteBuilder(listOf())
        val html = builder.build()
        Truth.assertThat(html.getByName("body")).isNotNull()
    }

    @Test
    fun `Correctly creates custom list tag `(){
        val builder = SiteBuilder(projects)
        val html = builder.build()

        val ul = html.getByName("ul")
        Truth.assertThat(ul).isNotNull()
        Truth.assertThat(ul!![0].getByName("li")).hasSize(3)
    }
}