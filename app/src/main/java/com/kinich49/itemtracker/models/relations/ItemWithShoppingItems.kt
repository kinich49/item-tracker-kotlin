package com.kinich49.itemtracker.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kinich49.itemtracker.models.ShoppingItem

data class ItemWithShoppingItems(
    @Embedded val item: CompositeItem,
    @Relation(
        parentColumn = "id",
        entityColumn = "item_id"
    )
    val shoppingItems: List<ShoppingItem>
)