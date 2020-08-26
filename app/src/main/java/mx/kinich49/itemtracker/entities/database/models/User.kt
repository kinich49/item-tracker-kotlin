package mx.kinich49.itemtracker.entities.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User(
    @PrimaryKey val id: Long,
    val username: String
)