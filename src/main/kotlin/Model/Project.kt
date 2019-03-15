package akjaw.Model

import akjaw.Exception.ProjectDataNull
import akjaw.Exception.ProjectDescriptionNotSet
import akjaw.Exception.ProjectJSONNull
import akjaw.Exception.ProjectNameNotSet
import com.beust.klaxon.JsonObject

class Project(projectJson: JsonObject?){
    val translationList: List<Translation>
    private lateinit var name: String

    init{
        translationList = getProjectValues(projectJson)
    }

    fun getProjectValues(projectJson: JsonObject?): List<Translation> {
        projectJson ?: throw ProjectJSONNull()
        return projectJson.keys.map { language ->
            val projectData = projectJson.obj(language)
            projectData ?: throw ProjectDataNull()

            val projectName = projectData.string("name") ?: throw ProjectNameNotSet()
            val projectDescriptionNotSet = projectData.string("description") ?: throw ProjectDescriptionNotSet()

            name = projectName

            Translation(
                language = language,
                name = projectName,
                description = projectDescriptionNotSet
            )
        }
    }

    override fun toString(): String {
        return name
    }
}