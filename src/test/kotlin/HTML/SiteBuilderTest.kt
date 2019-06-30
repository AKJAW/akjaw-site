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
        (jsonObject1["project1"] as JsonObject).addTechnologies(
            listOf("first tech", "second tech", "third tech"))

        projects = listOf(
            Project(jsonObject1)
        )
    }

    private fun createProjectJsonObject(name: String, h1: String): JsonObject{
        return JsonObject(mapOf(name to JsonObject(mapOf("h1" to h1))))
    }

    fun JsonObject.addTechnologies(technologies: List<String>){
        this["technologies"] = JsonArray(technologies)
    }

//    @Test
//    fun `it always creates a head tag`(){
//        val builder = SiteBuilder(listOf())
//        val html = builder.build()
//        println(html)
//        Truth.assertThat(html.get("head")).isNotNull()
//    }
}