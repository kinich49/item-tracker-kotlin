package com.kinich49.itemtracker.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.kinich49.itemtracker.models.database.Brand
import com.kinich49.itemtracker.models.database.Category

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
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "brand_id") val brandId: Long?,
    @ColumnInfo(name = "category_id") val categoryId: Long
)