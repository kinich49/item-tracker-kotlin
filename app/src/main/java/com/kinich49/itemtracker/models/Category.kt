package com.kinich49.itemtracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categories")
data class Category(
    @PrimaryKey val id: Long,
    val name: String
)