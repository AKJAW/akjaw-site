package akjaw

import akjaw.Exception.JSONParseExeption
import akjaw.Exception.ProjectJSONNull
import akjaw.Model.Project
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.io.File
import java.io.FileInputStream
import java.lang.Exception

class ProjectJSONParser(private val filePath: String){
    val projectFile: File = File(filePath)

    fun parse(name: String) : Any? {
        val cls = Parser::class.java
        val fileInputStream = FileInputStream(projectFile)

        return fileInputStream?.let { inputStream ->
            return Parser.default().parse(inputStream)
        }
    }

    fun getProjects(): List<Project> {
        val result = parse(filePath) as JsonArray<JsonObject>?
        result ?: throw JSONParseExeption()

        val projects = result.map {projectJSON ->
            Project(projectJSON)
        }

//        val projects = result.map {project ->
//            //            return@map project.keys.map { language ->
////                val projectData = project.obj(language)
////                projectData ?: throw ProjectJSONNull()
////                print(projectData)
////                Project(projectData)
////            }
////        }

        return projects
    }

    fun main(){
        val pjp = ProjectJSONParser("data/projects.json")
        pjp.getProjects()
    }
}

