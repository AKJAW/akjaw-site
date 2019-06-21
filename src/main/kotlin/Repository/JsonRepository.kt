package akjaw.Repository

import akjaw.JsonParser
import akjaw.Model.Project
import akjaw.Result.*
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonBase
import com.beust.klaxon.JsonObject
import java.lang.IllegalStateException

class JsonRepository(filePath: String): Repository{
    private val jsonProjects: JsonArray<JsonObject>

    init {
        val result = JsonParser.parse(filePath)

        val parsed = JsonParser.parse(filePath) then ::castToCorrectFormat

        if(parsed is Failure){
            throw IllegalStateException((result as Failure).errorMessage)
        }

        jsonProjects = (parsed as Success).value
    }

    private fun castToCorrectFormat(jsonBase: JsonBase): Result<JsonArray<JsonObject>> {
        return if(jsonBase is JsonArray<*>){
            Success(jsonBase as JsonArray<JsonObject>)
        } else {
            Failure("Cast to JsonArray containing JsonObject not successful")
        }
    }

    private val projectList: List<Project> by lazy {
        jsonProjects.map {
            Project(it)
        }
    }

    override fun getProjects(): List<Project> {
        return projectList
    }

}

fun main() {
    val projects = JsonRepository("data/projects.json").getProjects()
    val s = 's'
    val sss = 's'
    val ss = 's'
}