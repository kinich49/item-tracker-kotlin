package com.kinich49.itemtracker.models.database.relations

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded

@DatabaseView(
    "SELECT si.id as shopping_item_id, " +
            "si.unit_price, si.currency, si.quantity, " +
            "it.id as item_id, it.name as item_name" +
            "si.shopping_list_id " +
            "FROM Shopping_Items as si "
)
data class CompositeShoppingItem(
    @ColumnInfo(name = "id") val shoppingItemId: Long,
    @ColumnInfo(name = "unit_price") val unitPrice: Long,
    val currency: String,
    val quantity: Double,
    @ColumnInfo(name = "shopping_list_id") val shoppingLisId: Long,
    @Embedded val item: CompositeItem
)