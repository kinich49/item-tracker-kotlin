package com.kinich49.itemtracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Stores")
data class Store(
    @PrimaryKey val id: Long,
    val name: String
)