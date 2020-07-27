package com.kinich49.itemtracker.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.kinich49.itemtracker.models.database.Item
import com.kinich49.itemtracker.models.database.ShoppingList

@Entity(
    tableName = "Shopping_Items",
    foreignKeys = [
        ForeignKey(
            entity = Item::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("item_id")
        ),
        ForeignKey(
            entity = ShoppingList::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("shopping_list_id")
        )]
)
data class ShoppingItem(
    @PrimaryKey val id: Long,
    val currency: String = "MXN",
    val quantity: Double,
    @ColumnInfo(name = "unit_price") val unitPrice: Int,
    @ColumnInfo(name = "shopping_list_id") val shoppingListId: Long,
    @ColumnInfo(name = "item_id") val itemId: Long
)