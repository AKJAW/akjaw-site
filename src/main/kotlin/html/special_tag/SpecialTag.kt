package html.special_tag

interface SpecialTag{
    val signature: String

    fun isSpecialTag(name: String): Boolean
    fun createTag()
}