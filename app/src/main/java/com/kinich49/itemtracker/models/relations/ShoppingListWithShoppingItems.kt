package com.kinich49.itemtracker.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kinich49.itemtracker.models.ShoppingItem
import com.kinich49.itemtracker.models.ShoppingList

//data class ShoppingListWithShoppingItems(
//    @Embedded val shoppingList: ShoppingList,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "shopping_list_id"
//    )
//    val shoppingItems: List<ShoppingItem>
//)

class ShoppingListWithShoppingItems