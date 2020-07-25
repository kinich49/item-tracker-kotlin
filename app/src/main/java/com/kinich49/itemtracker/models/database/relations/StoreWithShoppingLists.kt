package com.kinich49.itemtracker.models.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kinich49.itemtracker.models.database.ShoppingList
import com.kinich49.itemtracker.models.database.Store

data class StoreWithShoppingLists(
    @Embedded val store: Store,
    @Relation(
        parentColumn = "id",
        entityColumn = "store_id"
    )
    val shoppingList: ShoppingList
)