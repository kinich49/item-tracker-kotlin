package com.kinich49.itemtracker.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kinich49.itemtracker.models.ShoppingList
import com.kinich49.itemtracker.models.Store

data class StoreWithShoppingLists(
    @Embedded val store: Store,
    @Relation(
        parentColumn = "id",
        entityColumn = "store_id"
    )
    val shoppingList: ShoppingList
)