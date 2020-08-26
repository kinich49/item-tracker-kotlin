package mx.kinich49.itemtracker.entities.database.models

import androidx.room.PrimaryKey

data class OfflineIdGenerator(
    @PrimaryKey val id: Long,
    val nextId: Long
)