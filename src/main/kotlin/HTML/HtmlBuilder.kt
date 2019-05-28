package akjaw

import akjaw.Model.Translation

object HTMLBuilder{
    fun html(init: (Tag.() -> Unit)? = null): Tag{
        return Tag("html").apply {
            init?.invoke(this)
        }
    }
}

class Tag(private val name: String){
    private val tagList = mutableListOf<Tag>()
    private val attributes = mutableListOf<Attribute>()
    private var textContent: String = ""

    fun tag(name: String, init: (Tag.() -> Unit)? = null) = initTag(Tag(name.toLowerCase()), init)

    private fun initTag(tag: Tag, init: (Tag.() -> Unit)?): Tag{
        if (init != null) {
            tag.init()
        }
        tagList.add(tag)
        return tag
    }

    operator fun String.unaryPlus(){
        textContent += this
    }

    operator fun Style.unaryPlus() {
        attributes.add(Attribute("style", this.rules))
    }

    operator fun Attributes.unaryPlus() {
        attributes.addAll(this.attrs)
    }

    override fun toString(): String {
        return """<$name${printAttributes()}>${tagList.fold("") {acc, tag -> "$acc$tag"}}$textContent</$name>"""
    }

    private fun printAttributes(): String{
        return if (attributes.isEmpty()){
            ""
        } else {
            attributes.joinToString(" ", " ") {
                "${it.name}=\"${it.value}\""
            }
        }
    }

    operator fun plus(s: String) {
        textContent += s
    }
}

class Attributes(vararg attributes: Pair<String, String>){
    val attrs = attributes.map {
        Attribute(it.first, it.second)
    }
}

data class Attribute(val name: String, val value: String)

class Style(vararg rules: Pair<String, String>){
    val rules = rules.joinToString("; ") {
        "${it.first}:${it.second}"
    }
}


