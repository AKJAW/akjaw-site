package akjaw.HTML

import java.lang.IllegalStateException
import java.lang.IndexOutOfBoundsException

object HTMLBuilder{
    fun html(init: (Tag.() -> Unit)? = null): Tag {
        return Tag("html").apply {
            init?.invoke(this)
        }
    }
}

class Tag(name: String){
    val name = name.toLowerCase()
    private val _tagList = mutableListOf<Tag>()
    val tagList: List<Tag> = _tagList
    val attributes = Attributes()
    var textContent: String = ""
        private set
    var className: String? = null
        private set
    var parent: Tag? = null
        private set

    fun tag(name: String, init: (Tag.() -> Unit)? = null) = initTag(Tag(name.toLowerCase()), init, this)

    private fun initTag(tag: Tag, init: (Tag.() -> Unit)?, parent: Tag): Tag {
        if (init != null) {
            tag.init()
        }
        tag.parent = parent
        _tagList.add(tag)
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

    fun addClass(classValue: String){
        val classNameOrEmptyString = className ?: ""
        val newClassName = "$classNameOrEmptyString $classValue"
        className = newClassName.trim().replace("\\s+".toRegex(), " ")
    }

    fun removeClass(classValue: String){
        val classes = className?.split(" ") ?: throw IllegalStateException("class is null")

        val newClasses = classes.filterNot {
            it == classValue
        }

        if(newClasses.size == classes.size){
            throw IllegalStateException("class $classValue does not is not inside className $className")
        }

        className = if(newClasses.isEmpty()){
            null
        } else {
            newClasses.joinToString(" ")
        }
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

    fun append(tag: Tag){
        insertAt(tagList.size, tag)
    }

    fun prepend(tag: Tag){
        insertAt(0, tag)
    }

    fun insertAt(index: Int, tag: Tag){
        _tagList.add(index, tag)
    }
}


