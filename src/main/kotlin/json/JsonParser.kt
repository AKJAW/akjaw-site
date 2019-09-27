package json

import com.beust.klaxon.JsonBase
import com.beust.klaxon.Parser

object JsonParser{

    private val parser = Parser.default()

    fun parse(filePath: String): Result<JsonBase> {
        return tryToParse(filePath).map(JsonParser::cast)
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

