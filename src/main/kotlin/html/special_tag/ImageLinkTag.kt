package html.special_tag

import html.Attributes
import html.Tag
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import html.TagBuilder
import java.lang.IllegalArgumentException

class ImageLinkTag: SpecialTag {
    override val signature: String = "imageLink"

    override fun createTag(tag: Tag, data: Any?, className: String?): Tag {

        return tag.apply{
            tag("div"){
                + Attributes("class" to "project-image-link ${className.orEmpty()}")

                val jsonObject = data as JsonObject

                createLink(jsonObject)
            }
        }
    }

    private fun Tag.createLink(jsonObject: JsonObject){
        tag("a"){

            val href = jsonObject["href"] as String?
                ?: throw IllegalArgumentException("href is empty in Link")

            val target = jsonObject["target"] as String? ?: "_blank"

            + Attributes("href" to href, "target" to target)

            val image = jsonObject["image"] as String?
                ?: throw IllegalArgumentException("image is empty in Link")

            createImage(image)

            val englishText = jsonObject["text-en"] as String?
                ?: throw IllegalArgumentException("text-en is empty in Link")

            val polishText = jsonObject["text-pl"] as String?

            createTitle(englishText, polishText)
        }
    }

    private fun Tag.createImage(imageLink: String){
        tag("img"){
            + Attributes("src" to imageLink)
        }
    }

    private fun Tag.createTitle(englishText: String, polishText: String?) {
        if(polishText != null){
            val languageJsonObject = JsonObject(mapOf("en" to englishText, "pl" to polishText))
            TagBuilder.createLanguageTagFromJsonObject(this, "span", languageJsonObject)
        } else {
            tag("span"){
                + englishText
            }
        }
    }
}
