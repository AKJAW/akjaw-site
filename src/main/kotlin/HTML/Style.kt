package akjaw

class Style(vararg rules: Pair<String, String>){
    val rules = rules.joinToString("; ") {
        "${it.first}:${it.second}"
    }
}