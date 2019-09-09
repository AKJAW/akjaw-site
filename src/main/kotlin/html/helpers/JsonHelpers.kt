package html.helpers

import java.lang.IllegalArgumentException

object JsonHelpers{
    data class JsonTagData(val tagName: String, val className: String? = null)

    fun parseJsonKey(key: String): JsonTagData {
        val split = key.split("-")
        if(split.size == 1){
            return JsonTagData(split[0])
        } else if(split.size > 2){
            throw IllegalArgumentException("The key contains more that one dash")
        }
        val (tagName, tagClass) = split

        return if(tagClass.toIntOrNull() != null){
            JsonTagData(tagName)
        } else {
            JsonTagData(tagName, tagClass)
        }

    }
}
