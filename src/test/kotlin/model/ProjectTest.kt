package model

import akjaw.Model.Project
import com.beust.klaxon.JsonObject
import com.google.common.truth.Truth
import org.junit.Test
import java.lang.ClassCastException
import java.lang.NullPointerException

class ProjectTest{

    @Test
    fun `it sets the root key as the project name`(){
        val root = JsonObject(mapOf("name" to JsonObject()))

        val project = Project(root)

        Truth.assertThat(project.name).isEqualTo("name")
    }

    @Test
    fun `projectData contains all nodes inside root value`(){
        val div = JsonObject(mapOf("h1" to "header", "span" to "span"))
        val root = JsonObject(mapOf("name" to div))

        val project = Project(root)

        Truth.assertThat(project.projectData).hasSize(2)
        Truth.assertThat(project.projectData["h1"]).isEqualTo("header")
        Truth.assertThat(project.projectData["span"]).isEqualTo("span")
    }

    @Test(expected = ClassCastException::class)
    fun `if root value is not a nested object then throw an error`(){
        val root = JsonObject(mapOf("name" to 1))
        val project = Project(root)
    }

    @Test(expected = NoSuchElementException::class)
    fun `if an empty object is passed throw an error`(){
        val root = JsonObject()
        val project = Project(root)
    }

    @Test(expected = NullPointerException::class)
    fun `if root has a null value throw an error`(){
        val root = JsonObject(mapOf("name" to null))
        val project = Project(root)
    }
}