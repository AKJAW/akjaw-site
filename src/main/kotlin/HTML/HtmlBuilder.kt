package akjaw

object HTMLBuilder{
    fun html(init: (Tag.() -> Unit)? = null): Tag{
        return Tag("html").apply {
            init?.invoke(this)
        }
    }
}

class Tag(name: String){
    val name = name.toLowerCase()
    val tagList = mutableListOf<Tag>()
    val attributes = Attributes()
    var textContent: String = ""

    fun tag(name: String, init: (Tag.() -> Unit)? = null) = initTag(Tag(name.toLowerCase()), init)

    private fun initTag(tag: Tag, init: (Tag.() -> Unit)?): Tag{
        if (init != null) {
            tag.init()
        }
        tagList.add(tag)
        return tag
    }

    operator fun String.unaryPlus(){
        textContent += this.toLowerCase()
    }

    operator fun Style.unaryPlus() {
        attributes.add(Attribute("style", this.rules))
    }

    operator fun Attributes.unaryPlus() {
        attributes.addAll(this.attrs)
    }

    fun toStringWithoutWhitespace(): String{
        return toString().replace(" ", "")
    }

    override fun toString(): String {
        return """<$name$attributes>${tagList.fold("") {acc, tag -> "$acc$tag"}}$textContent</$name>"""
    }

    operator fun plus(s: String) {
        textContent += s
    }
}


