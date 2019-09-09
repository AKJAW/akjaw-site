package akjaw

import com.beust.klaxon.JsonBase
import com.beust.klaxon.KlaxonException
import com.beust.klaxon.Parser
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.IllegalArgumentException

object JsonParser{
    private val parser = Parser.default()

    fun parse(filePath: String): Result<JsonBase> {
        return tryToParse(filePath).map(::cast)
    }

    private fun tryToParse(filePath: String): Result<Any> {
        return runCatching {
            parser.parse(filePath)
        }
    }

    private fun cast(json: Any): JsonBase{
        return json as JsonBase
    }

}

fun main(){
    val pjp = JsonParser.parse("data/projects.json")
}

