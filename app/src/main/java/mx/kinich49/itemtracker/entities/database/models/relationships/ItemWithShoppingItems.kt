package mx.kinich49.itemtracker.entities.database.models.relationships

import androidx.room.Embedded
import androidx.room.Relation
import mx.kinich49.itemtracker.entities.database.models.ShoppingItem

data class ItemWithShoppingItems(
    @Embedded val item: CompositeItem,
    @Relation(
        parentColumn = "id",
        entityColumn = "item_id"
    )
    val shoppingItems: List<ShoppingItem>
)