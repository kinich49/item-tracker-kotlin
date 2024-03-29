package mx.kinich49.itemtracker.entities.apis.deserializers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Deprecated(
    "This class can't serialize",
    ReplaceWith("LocalDateTypeAdapter")
)
class LocalDateDeserializer : JsonDeserializer<LocalDate> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDate {
        return LocalDate.parse(json?.asString, DateTimeFormatter.ISO_DATE)
    }
}