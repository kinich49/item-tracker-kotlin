package com.kinich49.itemtracker.models.database.relations

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.kinich49.itemtracker.models.database.Store
import java.time.LocalDate

data class CompositeShoppingList(
  @ColumnInfo(name = "shopping_list_id") val shoppingListId: Long,
  @ColumnInfo(name = "shopping_date") val shoppingDate: LocalDate,
  @Embedded(prefix = "store_") val store: Store
)