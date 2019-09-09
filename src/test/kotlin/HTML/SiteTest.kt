package HTML

import akjaw.HTML.TagBuilder
import akjaw.Model.Project
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.google.common.truth.Truth
import org.junit.Test
import java.io.File
import java.lang.NullPointerException

//class SiteTest{
//    private fun createProjectJsonObject(name: String, h1: String): JsonObject{
//        return JsonObject(mapOf(name to JsonObject(mapOf("h1" to h1))))
//    }
//
//    fun JsonObject.addList(key: String, listItems: List<Any>){
//        this[key] = JsonArray(listItems)
//    }
//
//    fun JsonObject.addObject(key: String, objectItems: Map<String, String>){
//        this[key] = JsonObject(objectItems)
//    }
//
//    @Test
//    fun `Always creates a head tag`(){
//        val builder = TagBuilder(listOf())
//        val html = builder.html
//        Truth.assertThat(html.getByName("head")).isNotNull()
//    }
//
//    @Test
//    fun `Always creates a body tag`(){
//        val builder = TagBuilder(listOf())
//        val html = builder.html
//        Truth.assertThat(html.getByName("body")).isNotNull()
//    }
//
//    @Test
//    fun `Always creates an about me section`(){
//        val builder = TagBuilder(listOf())
//        val html = builder.html
//
//        val section = html.getByClass("about-me")
//        Truth.assertThat(section).isNotNull()
//        Truth.assertThat(section).hasSize(1)
//    }
//
//    @Test
//    fun `Correctly hides one language by default`(){
//        val jsonObject1 = createProjectJsonObject("project1", "header1")
//        (jsonObject1["project1"] as JsonObject).addObject("h1", mapOf("en" to "english", "pl" to "polski"))
//        (jsonObject1["project1"] as JsonObject).addObject("span", mapOf("en" to "english", "pl" to "polski"))
//
//        (jsonObject1["project1"] as JsonObject)["div"] = JsonObject()
//        ((jsonObject1["project1"] as JsonObject)["div"] as JsonObject)
//            .addObject("span", mapOf("en" to "english", "pl" to "polski"))
//
//        val projects = listOf(
//            Project(jsonObject1)
//        )
//
//        val builder = TagBuilder(projects)
//        val html = builder.html
//
//        html.getByClass("pl")?.forEach {
//            Truth.assertThat(it.className).contains("none")
//        }
//    }
//
//    @Test
//    fun `Correctly creates custom list tag`(){
//        val jsonObject1 = createProjectJsonObject("project1", "header1")
//        (jsonObject1["project1"] as JsonObject).addList(
//            "list",
//            listOf("first tech", "second tech", "third tech"))
//
//        val projects = listOf(
//            Project(jsonObject1)
//        )
//
//        val builder = TagBuilder(projects)
//        val html = builder.html
//
//        val ul = html.getByName("ul")!!
//        val li = ul[0].getByName("li")!!
//        Truth.assertThat(li).hasSize(3)
//
//        Truth.assertThat(li[0].textContent).isEqualTo("first tech")
//        Truth.assertThat(li[1].textContent).isEqualTo("second tech")
//        Truth.assertThat(li[2].textContent).isEqualTo("third tech")
//    }
//
//    @Test
//    fun `Correctly creates language tags inside custom list tag`(){
//        val jsonObject1 = createProjectJsonObject("project1", "header1")
//        (jsonObject1["project1"] as JsonObject).addList(
//            "list",
//            listOf("first tech", JsonObject(mapOf("pl" to "drugi tech", "en" to "second tech")), "third tech"))
//
//        val projects = listOf(
//            Project(jsonObject1)
//        )
//
//        val builder = TagBuilder(projects)
//        val html = builder.html
//
//        val ul = html.getByName("ul")!!
//        val li = ul[0].getByName("li")!!
//        Truth.assertThat(li).hasSize(4)
//
//        Truth.assertThat(li[0].textContent).isEqualTo("first tech")
//        Truth.assertThat(li[1].textContent).isEqualTo("drugi tech")
//        Truth.assertThat(li[2].textContent).isEqualTo("second tech")
//        Truth.assertThat(li[3].textContent).isEqualTo("third tech")
//    }
//
//    @Test
//    fun `Correctly hides language tags inside custom list tag`(){
//        val jsonObject1 = createProjectJsonObject("project1", "header1")
//        (jsonObject1["project1"] as JsonObject).addList(
//            "list",
//            listOf("first tech", JsonObject(mapOf("pl" to "drugi tech", "en" to "second tech")), "third tech"))
//
//        val projects = listOf(
//            Project(jsonObject1)
//        )
//
//        val builder = TagBuilder(projects)
//        val html = builder.html
//
//        val ul = html.getByName("ul")!!
//        val li = ul[0].getByName("li")!!
//
//        Truth.assertThat(li[1].className).contains("none")
//    }
//
//    @Test
//    fun `Correctly creates custom projectTags tag`(){
//        val jsonObject1 = createProjectJsonObject("project1", "header1")
//        (jsonObject1["project1"] as JsonObject).addList(
//            "projectTags",
//            listOf("JavaScript", "React", "Firebase"))
//
//        val projects = listOf(
//            Project(jsonObject1)
//        )
//
//        val builder = TagBuilder(projects)
//        val html = builder.html
//
//        val div = html.getByClass("project-tags")
//        div ?: throw NullPointerException("project-tags cant  be null")
//        val tags = div[0].tagList
//        Truth.assertThat(tags).hasSize(3)
//        Truth.assertThat(tags[0].textContent).isEqualTo("JavaScript")
//        Truth.assertThat(tags[1].textContent).isEqualTo("React")
//        Truth.assertThat(tags[2].textContent).isEqualTo("Firebase")
//    }
//
//    @Test
//    fun `It correctly saves the html tree to a file`(){
//        val path = "src/test/resources/test_file.html"
//        File("src/test/resources/test_file.html").delete()
//
//        val builder = TagBuilder(listOf())
//        val html = builder.html
//
//        builder.saveToFile(path)
//
//        Truth.assertThat(File(path).exists()).isTrue()
//        val fileContent = File(path).readText()
//        Truth.assertThat(fileContent.contains("<head>")).isTrue()
//        Truth.assertThat(fileContent.contains("<body>")).isTrue()
//
//    }
//}