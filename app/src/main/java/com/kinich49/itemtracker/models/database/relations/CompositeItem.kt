package com.kinich49.itemtracker.models.database.relations

import androidx.room.Embedded
import com.kinich49.itemtracker.models.database.Brand
import com.kinich49.itemtracker.models.database.Category
import com.kinich49.itemtracker.models.database.Item

data class CompositeItem(
    @Embedded(prefix = "brand_") val brand: Brand,
    @Embedded(prefix = "category_") val category: Category,
    @Embedded(prefix = "item_") val item: Item
)