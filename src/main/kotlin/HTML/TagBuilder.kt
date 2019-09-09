package akjaw.HTML

import akjaw.Model.Project
import akjaw.Repository.JsonRepository
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import java.io.File
import java.lang.IllegalStateException

object TagBuilder{

    fun createTagWithLanguages(
        parentTag: Tag,
        tagName: String,
        englishTextContent: String,
        polishTextContent: String,
        classes: String? = null){

        val classValue = if(classes == null){
            ""
        } else {
            " $classes"
        }

        parentTag.apply {
            tag(tagName){
                + Attributes("class" to "en $classValue")
                + englishTextContent
            }

            tag(tagName){
                + Attributes("class" to "pl none $classValue")
                + polishTextContent
            }
        }
    }

}
