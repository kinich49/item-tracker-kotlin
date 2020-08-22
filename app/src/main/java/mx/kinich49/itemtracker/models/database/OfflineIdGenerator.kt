package mx.kinich49.itemtracker.models.database

import androidx.room.PrimaryKey

data class OfflineIdGenerator(
    @PrimaryKey val id: Long,
    val nextId: Long
)