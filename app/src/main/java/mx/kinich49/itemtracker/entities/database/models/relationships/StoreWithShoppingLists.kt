package mx.kinich49.itemtracker.entities.database.models.relationships

import androidx.room.Embedded
import androidx.room.Relation
import mx.kinich49.itemtracker.entities.database.models.ShoppingList
import mx.kinich49.itemtracker.entities.database.models.Store

data class StoreWithShoppingLists(
    @Embedded val store: Store,
    @Relation(
        parentColumn = "id",
        entityColumn = "store_id"
    )
    val shoppingList: ShoppingList
)