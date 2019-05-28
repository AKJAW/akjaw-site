package akjaw

import akjaw.Exception.JSONParseExeption
import akjaw.Model.Project
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.io.File
import java.io.FileInputStream
import java.lang.IllegalStateException

object JsonParser{

    fun from(filePath: String): JsonArray<JsonObject>{
        val cls = Parser::class.java
        val fileInputStream = FileInputStream(filePath)

        return fileInputStream.let { inputStream ->
            Parser.default().parse(inputStream) as? JsonArray<JsonObject>
                ?:throw IllegalStateException("$filePath is not a JSON")
        }
        val s = 's'
    }

//    var projects = getProjects(filePath)

//    private fun getProjects(filePath: String): List<Project> {
//        val result = parse(filePath) as? JsonArray<JsonObject>? ?: throw IllegalStateException("$filePath is not a JSON")
//
//        val projects = result.map {projectJSON ->
//            Project(projectJSON)
//        }
//
////        val projects = result.map {project ->
////            //            return@map project.keys.map { language ->
//////                val projectData = project.obj(language)
//////                projectData ?: throw ProjectJSONNull()
//////                print(projectData)
//////                Project(projectData)
//////            }
//////        }
//
//        return projects
//    }



}

fun main(){
    val pjp = JsonParser.from("data/projects.json")
}

