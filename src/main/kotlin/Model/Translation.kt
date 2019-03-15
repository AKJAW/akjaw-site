package akjaw.Model

import com.beust.klaxon.JsonObject
import javax.management.Descriptor

data class Translation(
    val language: String,
    val name: String,
    val description: String
)