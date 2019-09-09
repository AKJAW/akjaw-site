package akjaw.HTML

import akjaw.Model.Project
import akjaw.Repository.JsonRepository
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import html.helpers.parseJsonKey
import java.io.File
import java.lang.IllegalStateException

object TagBuilder{

    fun createLanguageTagFromJsonObject(tag: Tag, key: String, parsed: JsonObject) {
        val (tagName, tagClass) = parseJsonKey(key)

        tag.apply {
            TagBuilder.createTagWithLanguages(
                this,
                tagName,
                parsed.getValue("en") as String,
                parsed.getValue("pl") as String,
                tagClass

            )
        }

    }

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
