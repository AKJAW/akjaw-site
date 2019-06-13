package akjaw.Repository

import akjaw.JsonParser
import akjaw.Model.Project
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject

class Repository(filePath: String): JsonRepository{
    private val jsonProjects: JsonArray<JsonObject> by lazy {
        JsonParser.from(filePath) as JsonArray<JsonObject>
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
    val projects = Repository("data/projects.json").getProjects()
    val s = 's'
    val sss = 's'
    val ss = 's'
}