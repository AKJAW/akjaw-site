package html.special_tag

import html.Attributes
import html.Tag
import com.beust.klaxon.JsonArray
import html.TagBuilder
import java.lang.IllegalArgumentException

class GalleryTag: SpecialTag {
    enum class ArrowDirection(val className: String){
        LEFT("prev"),
        RIGHT("next")
    }

    override val signature: String = "gallery"

    override fun createTag(tag: Tag, data: Any?, className: String?): Tag {

        return tag.apply{
            tag("div"){
                + Attributes("class" to "slider gallery ${className.orEmpty()}")
                createFrame(data as JsonArray<*>)
                createArrows()
                createDots()
            }

        }
    }

    private fun Tag.createFrame(jsonArray: JsonArray<*>){
        tag("div"){
            + Attributes("class" to "frame js_frame")

            tag("div") {
                +Attributes("class" to "slides js_slides")

                iterateOverImagePaths(jsonArray)
            }
        }
    }

    private fun Tag.iterateOverImagePaths(jsonArray: JsonArray<*>) {
        jsonArray.forEach {
            when (it) {
                is String -> {
                    createImageTag(it)
                }
                else -> {
                    throw IllegalArgumentException("JsonArray item has incorrect type")
                }
            }
        }
    }

    private fun Tag.createImageTag(path: String){
        tag("li"){
            + Attributes("class" to "js_slide")

            tag("img"){
                + Attributes(
                    "draggable" to "false",
                    "src" to path
                    )
            }
        }
    }

    private fun Tag.createArrows(){
        arrow(ArrowDirection.LEFT)
        arrow(ArrowDirection.RIGHT)
    }

    private fun Tag.arrow(arrowDirection: ArrowDirection){
        tag("span"){
            val className = arrowDirection.className
            + Attributes("class" to "js_$className $className")

            when (arrowDirection){
                ArrowDirection.LEFT -> {
                    + "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"50\" height=\"50\" viewBox=\"0 0 501.5 501.5\"><g><path fill=\"#2E435A\" d=\"M302.67 90.877l55.77 55.508L254.575 250.75 358.44 355.116l-55.77 55.506L143.56 250.75z\"></path></g></svg>"
                }
                ArrowDirection.RIGHT -> {
                    + "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"50\" height=\"50\" viewBox=\"0 0 501.5 501.5\"><g><path fill=\"#2E435A\" d=\"M199.33 410.622l-55.77-55.508L247.425 250.75 143.56 146.384l55.77-55.507L358.44 250.75z\"></path></g></svg>"
                }
            }
        }
    }

    private fun Tag.createDots(){
        tag("ul"){
            + Attributes("class" to "js_dots dots")
        }
    }
}
//<div class="slider js_simple_dots simple">
    //<div class="frame js_frame">
        //<ul class="slides js_slides">
            //<li class="js_slide"><img draggable="false" src="../assets/faltur/error.JPG"/></li>
            //<li class="js_slide"><img draggable="false" src="../assets/faltur/afterUpdate2.JPG"/></li>
            //<li class="js_slide"><img draggable="false" src="../assets/faltur/afterUpdate.JPG"/></li>
            //<li class="js_slide"><img draggable="false" src="../assets/faltur/afterRead.JPG"/></li>
        //</ul>
    //</div>
    //
    //<span class="js_prev prev">
        //<svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" viewBox="0 0 501.5 501.5"><g><path fill="#2E435A" d="M302.67 90.877l55.77 55.508L254.575 250.75 358.44 355.116l-55.77 55.506L143.56 250.75z"></path></g></svg>
    //</span>
    //
    //<span class="js_next next">
        //<svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" viewBox="0 0 501.5 501.5"><g><path fill="#2E435A" d="M199.33 410.622l-55.77-55.508L247.425 250.75 143.56 146.384l55.77-55.507L358.44 250.75z"></path></g></svg>
    //</span>
    //
    //<ul class="js_dots dots"></ul>
//</div>