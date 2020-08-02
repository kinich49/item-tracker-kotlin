package com.kinich49.itemtracker.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "Shopping_Lists",
    foreignKeys = [
        ForeignKey(
            entity = Store::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("store_id")
        )]
)
data class ShoppingList(
    @PrimaryKey val id: Long? = null,
    @ColumnInfo(name = "shopping_date") val shoppingDate: LocalDate,
    @ColumnInfo(name = "store_id") val storeId: Long,
    var state: Int = 0
)