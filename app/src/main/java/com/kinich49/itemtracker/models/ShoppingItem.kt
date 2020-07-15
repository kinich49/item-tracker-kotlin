package com.kinich49.itemtracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Shopping_Items")
data class ShoppingItem(
    @PrimaryKey val id: Long,
    val currency: String = "MXN",
    val quantity: Double,
    val unitPrice: Int,
    @ColumnInfo(name = "shopping_list_id") val shoppingListId: Long
)