package mx.kinich49.itemtracker.entities.database.models.relationships

import androidx.room.Embedded
import mx.kinich49.itemtracker.entities.database.models.Brand
import mx.kinich49.itemtracker.entities.database.models.Category
import mx.kinich49.itemtracker.entities.database.models.Item

data class CompositeItem(
    @Embedded(prefix = "brand_") val brand: Brand?,
    @Embedded(prefix = "category_") val category: Category,
    @Embedded(prefix = "item_") val item: Item
)