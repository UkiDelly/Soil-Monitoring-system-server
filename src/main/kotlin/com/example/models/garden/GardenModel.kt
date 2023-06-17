package com.example.models.garden

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.Document
import org.bson.types.ObjectId
import java.util.*


@Serializable
enum class PlantType(val code: String) {
    @SerialName("rice")
    RICE("rice"),

    @SerialName("corn")
    CORN("corn"),

    @SerialName("cassava")
    CASSAVA("cassava"),
}

@Serializable
data class GardenModel(
    @Contextual
    val id: ObjectId? = null,
    val name: String,
    val notes: String,
    val plant: PlantType,
    @Contextual
    val createdBy: ObjectId,
    @Contextual
    val createdAt: Date = Date()
) {


    companion object {
        fun fromDocument(document: Document): GardenModel {
            return GardenModel(
                id = document.getObjectId("_id"),
                name = document.getString("name"),
                notes = document.getString("notes"),
                // Document에 저장된 plant를 가져와서 PlantType의 code와 비교하여 일치하는 값을 GardenModel의 plant에 저장.
                plant = document.getString("plant").let { plant -> PlantType.values().first { it.code == plant } },
                createdBy = document.getObjectId("createdBy"),
                createdAt = document.getDate("createdAt"),
            )
        }
    }

    fun toDocument(): Document {
        return Document(
            mapOf(

                "name" to name,
                "notes" to notes,
                "plant" to plant.name.lowercase(),
                "createdBy" to createdBy,
                "createdAt" to createdAt
            )

        )
    }
}
