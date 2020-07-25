package com.kinich49.itemtracker.models.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Brands")
data class Brand(
    @PrimaryKey val id: Long,
    val name: String
)