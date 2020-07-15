package com.kinich49.itemtracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User(
    @PrimaryKey val id: Long,
    val username: String
)