package mx.kinich49.itemtracker.entities.database.models.relationships

import androidx.room.Embedded
import androidx.room.Relation
import mx.kinich49.itemtracker.entities.database.models.Brand
import mx.kinich49.itemtracker.entities.database.models.Item

data class BrandWithItems(
    @Embedded val brand: Brand,
    @Relation(
        parentColumn = "id",
        entityColumn = "brand_id"
    )
    val items: List<Item>
)