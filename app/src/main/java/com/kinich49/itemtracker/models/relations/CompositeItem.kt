package com.kinich49.itemtracker.models.relations

import androidx.room.Embedded
import com.kinich49.itemtracker.models.Brand
import com.kinich49.itemtracker.models.Category
import com.kinich49.itemtracker.models.Item

data class CompositeItem(
    @Embedded(prefix = "brand_") val brand: Brand,
    @Embedded(prefix = "category_") val category: Category,
    @Embedded(prefix = "item_") val item: Item
)