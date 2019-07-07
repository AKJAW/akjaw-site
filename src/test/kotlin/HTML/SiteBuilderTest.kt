package HTML

import akjaw.HTML.SiteBuilder
import akjaw.Model.Project
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.google.common.truth.Truth
import org.junit.Test

class SiteBuilderTest{
    private fun createProjectJsonObject(name: String, h1: String): JsonObject{
        return JsonObject(mapOf(name to JsonObject(mapOf("h1" to h1))))
    }

    fun JsonObject.addList(listItems: List<String>){
        this["list"] = JsonArray(listItems)
    }

    @Test
    fun `Always creates a head tag`(){
        val builder = SiteBuilder(listOf())
        val html = builder.build()
        Truth.assertThat(html.getByName("head")).isNotNull()
    }

    @Test
    fun `Always creates a body tag`(){
        val builder = SiteBuilder(listOf())
        val html = builder.build()
        Truth.assertThat(html.getByName("body")).isNotNull()
    }

    @Test
    fun `Always creates an about me section`(){
        //TODO
        throw NotImplementedError()
    }

    @Test
    fun `Correctly creates custom list tag`(){
        val jsonObject1 = createProjectJsonObject("project1", "header1")
        (jsonObject1["project1"] as JsonObject).addList(
            listOf("first tech", "second tech", "third tech"))

        val projects = listOf(
            Project(jsonObject1)
        )

        val builder = SiteBuilder(projects)
        val html = builder.build()

        val ul = html.getByName("ul")!!
        val li = ul[0].getByName("li")!!
        Truth.assertThat(li).hasSize(3)

        Truth.assertThat(li[0].textContent).isEqualTo("first tech")
        Truth.assertThat(li[1].textContent).isEqualTo("second tech")
        Truth.assertThat(li[2].textContent).isEqualTo("third tech")
    }


}