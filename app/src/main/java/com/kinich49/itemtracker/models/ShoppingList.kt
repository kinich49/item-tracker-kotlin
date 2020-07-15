package com.kinich49.itemtracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "Shopping_Lists")
data class ShoppingList(
    @PrimaryKey val id: Long,
    val shoppingDate: LocalDate,
    @ColumnInfo(name = "store_id") val storeId: Long
)