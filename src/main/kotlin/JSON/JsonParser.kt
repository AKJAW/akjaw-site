package akjaw

import akjaw.Model.Project
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonBase
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.io.File
import java.io.FileInputStream
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

object JsonParser{
    val parser = Parser.default()

    fun from(filePath: String): JsonBase{
//        val fileInputStream = FileInputStream(filePath)

//        return fileInputStream.let { inputStream ->
        return parser.parse(filePath) as? JsonBase
                ?:throw IllegalArgumentException("$filePath is not a JSON")
//        }
    }
}

fun main(){
    val pjp = JsonParser.from("data/projects.json")
}

