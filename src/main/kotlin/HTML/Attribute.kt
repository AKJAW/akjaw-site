package akjaw.HTML

data class Attribute(val name: String, val value: String){
    override fun toString(): String {
        return """$name="$value""""
    }
}