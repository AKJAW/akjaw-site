package html

import akjaw.HTML.Attributes
import akjaw.HTML.Tag
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import html.helpers.JsonHelpers.parseJsonKey
import java.lang.IllegalArgumentException

object TagBuilder{

    fun createTagsFromFlatJsonArray(
        tag: Tag,
        jsonArray: JsonArray<*>,
        tagName: String,
        operationOnTag: Tag.() -> Unit = {}){
        tag.apply {
            jsonArray.forEach {
                when (it) {
                    is JsonObject -> {
                        TagBuilder.createLanguageTagFromJsonObject(this, tagName, it, operationOnTag)
                    }
                    is String -> {
                        createStringItemTag(it, tagName, operationOnTag)
                    }
                    else -> {
                        throw IllegalArgumentException("JsonArray item has incorrect type")
                    }
                }
            }
        }
    }

    private fun Tag.createStringItemTag(
        textContent: String,
        tagName: String,
        operationOnTag: Tag.() -> Unit
    ){
        tag(tagName) {
            + textContent
            operationOnTag()
        }
    }

    fun createLanguageTagFromJsonObject(
        tag: Tag,
        key: String,
        parsed: JsonObject,
        operationOnTag: Tag.() -> Unit = {}
    ) {
        val (tagName, tagClass) = parseJsonKey(key)

        tag.apply {
            TagBuilder.createTagWithLanguages(
                this,
                tagName,
                parsed.getValue("en") as String,
                parsed.getValue("pl") as String,
                operationOnTag,
                tagClass
            )
        }

    }

    fun createTagWithLanguages(
        parentTag: Tag,
        tagName: String,
        englishTextContent: String,
        polishTextContent: String,
        operationInItem: Tag.() -> Unit = {},
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
                operationInItem()
            }

            tag(tagName){
                + Attributes("class" to "pl none $classValue")
                + polishTextContent
                operationInItem()
            }
        }
    }

}
