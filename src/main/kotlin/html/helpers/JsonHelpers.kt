package html.helpers

fun parseJsonKey(key: String): Pair<String, String?> {
    val split = key.split("-")
    if(split.size == 1){
        return split[0] to null
    }
    val (tagName, tagClass) = split

    return if(tagClass.toIntOrNull() != null){
        tagName to null
    } else {
        tagName to tagClass
    }

}