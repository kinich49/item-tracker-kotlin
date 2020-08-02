package mx.kinich49.itemtracker.models.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import mx.kinich49.itemtracker.models.database.ShoppingItem

data class ItemWithShoppingItems(
    @Embedded val item: CompositeItem,
    @Relation(
        parentColumn = "id",
        entityColumn = "item_id"
    )
    val shoppingItems: List<ShoppingItem>
)