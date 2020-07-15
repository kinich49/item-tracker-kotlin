package com.kinich49.itemtracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Brands")
data class Brand(
    @PrimaryKey val id: Long,
    val name: String
)