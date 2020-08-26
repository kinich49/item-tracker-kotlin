package mx.kinich49.itemtracker.entities.database.models.relationships

import androidx.room.Embedded
import androidx.room.Relation
import mx.kinich49.itemtracker.entities.database.models.ShoppingItem
import mx.kinich49.itemtracker.entities.database.models.ShoppingList

data class ShoppingListWithShoppingItems(
    @Embedded val shoppingList: ShoppingList,
    @Relation(
        parentColumn = "id",
        entityColumn = "shopping_list_id"
    )
    val shoppingItems: List<ShoppingItem>
)