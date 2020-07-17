package com.kinich49.itemtracker.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kinich49.itemtracker.models.Brand
import com.kinich49.itemtracker.models.Item

data class BrandWithItems(
    @Embedded val brand: Brand,
    @Relation(
        parentColumn = "id",
        entityColumn = "brand_id"
    )
    val items: List<Item>
)