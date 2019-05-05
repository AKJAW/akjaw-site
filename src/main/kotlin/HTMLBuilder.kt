package akjaw

import akjaw.Model.Translation

class HTMLBuilder(val projectList: List<List<Translation>>){
//    val html: Tag

    init{
//        html = Tag("html").apply{
//            add(createHead())
//            add(createBody())
//        }
//        val head = Tag("head")
//        html.add(createBody())

//        val htmlTree = html {
//            body {
//                + "Ale bomba"
//            }
//        }

        val htmlTree = tag("html") {
            tag("body") {
                + "Ale bomba"
            }
        }

        println(htmlTree)
    }

    private fun tag(name: String, init: Tag.() -> Tag?): Tag{
        return Tag(name).apply {
            add(init())
        }
    }

    class Tag(private val name: String){
        private val tagList = mutableListOf<Tag>()
        private var textContent: String = ""

        fun add(tag: Tag?){
            tag?.let {
                tagList.add(it)
            }
        }

        operator fun String.unaryPlus(): Tag?{
            textContent += this
            return null
        }

        override fun toString(): String {
            return """<$name>${tagList.fold("") {acc, tag -> "$acc$tag"}}$textContent</$name>"""
        }
    }


}

fun main(){
    val mutableList = mutableListOf<List<Translation>>()
    mutableList.add(listOf(Translation(language="pl", name="Logo-quiz web", description="no fajna apka"), Translation(language="en", name="Logo-quiz web", description="cool app")))
    mutableList.add(listOf(Translation(language="pl", name="akjTimer", description="ayy"), Translation(language="en", name="akjTimer", description="lmaro"), Translation(language="en", name="Logo-quiz web", description="cool app")))

    HTMLBuilder(mutableList)
}