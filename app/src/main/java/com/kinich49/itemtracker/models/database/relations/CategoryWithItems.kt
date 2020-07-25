package com.kinich49.itemtracker.models.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.kinich49.itemtracker.models.database.Category
import com.kinich49.itemtracker.models.database.Item

data class CategoryWithItems(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val items: List<Item>
)