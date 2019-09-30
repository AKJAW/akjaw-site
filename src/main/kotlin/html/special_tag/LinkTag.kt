package html.special_tag

import html.Attributes
import html.Tag
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import html.TagBuilder
import java.lang.IllegalArgumentException

class LinkTag: SpecialTag {
    override val signature: String = "link"

    override fun createTag(tag: Tag, data: Any?, className: String?): Tag {

        return tag.apply{
            tag("a"){
                if (className != null){
                    + Attributes("class" to className)
                }

                val jsonObject = data as JsonObject

                val href = jsonObject["href"] as String?
                    ?: throw IllegalArgumentException("href is empty in Link")

                val target = jsonObject["target"] as String? ?: "_blank"
                + Attributes("href" to href, "target" to target)

                val englishText = jsonObject["text-en"] as String?
                    ?: throw IllegalArgumentException("text-en is empty in Link")

                val polishText = jsonObject["text-pl"] as String? ?: englishText

                val languageJsonObject = JsonObject(mapOf("en" to englishText, "pl" to polishText))
                TagBuilder.createLanguageTagFromJsonObject(this, "span", languageJsonObject)


            }
        }
    }
}