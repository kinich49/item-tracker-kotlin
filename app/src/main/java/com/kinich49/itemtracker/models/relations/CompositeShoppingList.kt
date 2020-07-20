package com.kinich49.itemtracker.models.relations

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation
import com.kinich49.itemtracker.models.ShoppingItem
import com.kinich49.itemtracker.models.ShoppingList
import com.kinich49.itemtracker.models.Store
import java.time.LocalDate

data class CompositeShoppingList(
    @ColumnInfo(name = "shopping_list_id") val shoppingListId: Long,
    @ColumnInfo(name = "shopping_date") val shoppingDate: LocalDate,
    @Embedded(prefix = "store_") val store: Store
)