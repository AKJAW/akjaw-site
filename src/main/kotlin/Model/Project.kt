package akjaw.Model

import com.beust.klaxon.JsonObject
import java.lang.NullPointerException

class Project(projectJson: JsonObject){
    val name: String = projectJson.keys.first()
    val projectData: JsonObject = projectJson.obj(name) ?: throw NullPointerException("Project data is null")
}
