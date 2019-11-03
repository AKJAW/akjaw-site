package repository

import model.Project
import json.JsonParser
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonBase
import com.beust.klaxon.JsonObject

class JsonRepository(filePath: String): Repository {
    private val jsonProjects: JsonArray<JsonObject>
    override val projects: List<Project>

    init {
        val parsed = JsonParser.parse(filePath).map(::castToCorrectFormat)

        if(parsed.isSuccess){
            jsonProjects = parsed.getOrThrow()

            projects = jsonProjects.map {
                Project(it)
            }
        } else {
            throw parsed.exceptionOrNull()!!
        }
    }

    private fun castToCorrectFormat(jsonBase: JsonBase): JsonArray<JsonObject> {
        @Suppress("UNCHECKED_CAST")
        return jsonBase as JsonArray<JsonObject>
    }

}

fun main() {
    val projects = JsonRepository("data/projects.json").projects
}