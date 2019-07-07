package akjaw.HTML

object HTMLBuilder{
    fun html(init: (Tag.() -> Unit)? = null): Tag {
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
    var className: String? = null

    fun tag(name: String, init: (Tag.() -> Unit)? = null) = initTag(Tag(name.toLowerCase()), init)

    private fun initTag(tag: Tag, init: (Tag.() -> Unit)?): Tag {
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
        attributes.add(Attribute("style", this.toString()))
    }

    operator fun Attributes.unaryPlus() {
        className = this.firstOrNull{
            it.name == "class"
        }?.value
        attributes.addAll(this)
    }

    override fun toString(): String {
        return """<$name$attributes>${tagList.fold("") {acc, tag -> "$acc$tag"}}$textContent</$name>"""
    }

    operator fun plus(s: String) {
        textContent += s
    }

    fun getByName(tagName: String): List<Tag>? {
        return recursiveFindWithPredicate {
            it.name == tagName
        }

    }

    fun getByClass(classNameToFind: String): List<Tag>? {
        return recursiveFindWithPredicate {
            val tagClass = it.className

            if(tagClass == null){
                false
            } else {
                tagClass == classNameToFind
            }
        }
    }

    private fun recursiveFindWithPredicate(predicate: ((Tag) -> Boolean)): List<Tag>? {
        val foundTags = mutableListOf<Tag>()

        tagList.forEach {

            if(predicate(it)){
                foundTags.add(it)
            }

            val childFoundTags = it.recursiveFindWithPredicate(predicate)
            if(childFoundTags != null && childFoundTags.isNotEmpty()){
                foundTags.addAll(childFoundTags)
            }
        }

        return if(foundTags.isEmpty()){
            null
        } else {
            foundTags.toList()
        }
    }
}


