package mx.kinich49.itemtracker.entities.database.models.relationships

import androidx.room.Embedded
import androidx.room.Relation
import mx.kinich49.itemtracker.entities.database.models.Category
import mx.kinich49.itemtracker.entities.database.models.Item

data class CategoryWithItems(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val items: List<Item>
)