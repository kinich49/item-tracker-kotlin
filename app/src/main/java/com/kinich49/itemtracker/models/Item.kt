package com.kinich49.itemtracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Items",
    foreignKeys = [ForeignKey(
        entity = Brand::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("brand_id")
    ),
        ForeignKey(
            entity = Category::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("category_id")
        )]
)
data class Item(
    @PrimaryKey val id: Long,
    val name: String,
    @ColumnInfo(name = "brand_id") val brandId: Long,
    @ColumnInfo(name = "category_id") val categoryId: Long
)