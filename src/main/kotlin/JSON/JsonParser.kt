package akjaw

import akjaw.Result.*
import com.beust.klaxon.JsonBase
import com.beust.klaxon.KlaxonException
import com.beust.klaxon.Parser
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.IllegalArgumentException

object JsonParser{
    private val parser = Parser.default()

    fun parse(filePath: String): Result<JsonBase> {
        return filePath into ::tryToParse then ::cast
    }

    private fun tryToParse(filePath: String): Result<Any>{
        return try{
            val json = parser.parse(filePath)
            Success(json)
        } catch (exception: FileNotFoundException){
            Failure("File does not exist")
        } catch (exception: KlaxonException){
            Failure("Could not parse json")
        }
    }

    private fun cast(json: Any): Result<JsonBase>{
        return if(json is JsonBase){
            Success(json)
        } else {
            Failure("Could not cast")
        }
    }

}

fun main(){
    val pjp = JsonParser.parse("data/projects.json")
}

