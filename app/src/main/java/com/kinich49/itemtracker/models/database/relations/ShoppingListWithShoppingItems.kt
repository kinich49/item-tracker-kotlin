package com.kinich49.itemtracker.models.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kinich49.itemtracker.models.database.ShoppingItem
import com.kinich49.itemtracker.models.database.ShoppingList

data class ShoppingListWithShoppingItems(
    @Embedded val shoppingList: ShoppingList,
    @Relation(
        parentColumn = "id",
        entityColumn = "shopping_list_id"
    )
    val shoppingItems: List<ShoppingItem>
)