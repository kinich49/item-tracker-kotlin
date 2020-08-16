package mx.kinich49.itemtracker.models.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Stores")
data class Store(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val name: String,
    var state: Int = 0
)