package com.kinich49.itemtracker.models.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val name: String,
    var state: Int = 0
)