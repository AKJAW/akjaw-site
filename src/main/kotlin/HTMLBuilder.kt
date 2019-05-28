package akjaw

import akjaw.Model.Translation

class HTMLBuilder(val projectList: List<List<Translation>>){
//    val html: Tag

    init{
        val htmlTree = html {
            tag("Head")
            tag("body"){
                tag("p"){
                    + "pawel"
                    + Style(
                        "display" to "inline")
                }
                tag("a"){
                    + Attributes(
                        "href" to "akjaw.com")

                    + Style(
                        "color" to "black",
                        "width" to "100px")
                }
            }
        }


        println(htmlTree)
    }

    private fun html(init: (Tag.() -> Unit)? = null): Tag{
        return Tag("html").apply {
            init?.invoke(this)
        }
    }


    class Tag(private val name: String){
        private val tagList = mutableListOf<Tag>()
        private val attributes = mutableListOf<Attribute>()
        private var textContent: String = ""

        fun tag(name: String, init: (Tag.() -> Unit)? = null) = initTag(Tag(name.toLowerCase()), init)

//        fun attributes

//        fun style(init: Tag.() -> Map<String, String>): String{
//            val styleMap = init()
//            val styleAttribute =
//
//            attributes.add(Attribute("style", styleAttribute))
//            return styleAttribute
//
//        }

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

        operator fun Map<String, String>.unaryPlus(){
            val addedAttributes = this
                .toList()
                .map {
                    Attribute(it.first, it.second)
                }

            attributes.addAll(addedAttributes)
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
}

fun main(){
    val mutableList = mutableListOf<List<Translation>>()
    mutableList.add(listOf(Translation(language="pl", name="Logo-quiz web", description="no fajna apka"), Translation(language="en", name="Logo-quiz web", description="cool app")))
    mutableList.add(listOf(Translation(language="pl", name="akjTimer", description="ayy"), Translation(language="en", name="akjTimer", description="lmaro"), Translation(language="en", name="Logo-quiz web", description="cool app")))

    HTMLBuilder(mutableList)
}


//package akjaw
//
//import akjaw.Model.Translation
//
//class HTMLBuilder(val projectList: List<List<Translation>>){
////    val html: Tag
//
//    init{
////        html = Tag("html").apply{
////            add(createHead())
////            add(createBody())
////        }
////        val head = Tag("head")
////        html.add(createBody())
//
////        val htmlTree = html {
////            body {
////                + "Ale bomba"
////            }
////        }
//
//        val htmlTree = tag("html") {
//            tag("body") {
//                + "Ale bomba"
//            }
//        }
//
//        println(htmlTree)
//    }
//
//    private fun tag(name: String, init: Tag.() -> Tag?): Tag{
//        return Tag(name).apply {
//            add(init())
//        }
//    }
//
//    class Tag(private val name: String){
//        private val tagList = mutableListOf<Tag>()
//        private var textContent: String = ""
//
//        fun add(tag: Tag?){
//            tag?.let {
//                tagList.add(it)
//            }
//        }
//
//        operator fun String.unaryPlus(): Tag?{
//            textContent += this
//            return null
//        }
//
//        override fun toString(): String {
//            return """<$name>${tagList.fold("") {acc, tag -> "$acc$tag"}}$textContent</$name>"""
//        }
//    }
//
//
//}
//
//fun main(){
//    val mutableList = mutableListOf<List<Translation>>()
//    mutableList.add(listOf(Translation(language="pl", name="Logo-quiz web", description="no fajna apka"), Translation(language="en", name="Logo-quiz web", description="cool app")))
//    mutableList.add(listOf(Translation(language="pl", name="akjTimer", description="ayy"), Translation(language="en", name="akjTimer", description="lmaro"), Translation(language="en", name="Logo-quiz web", description="cool app")))
//
//    HTMLBuilder(mutableList)
//}