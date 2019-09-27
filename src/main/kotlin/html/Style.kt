package html

class Style(vararg rules: Pair<String, String>){
    private val rules = rules.joinToString("; ") {
        "${it.first.toLowerCase()}:${it.second.toLowerCase()}"
    }

    override fun toString(): String = rules
}
