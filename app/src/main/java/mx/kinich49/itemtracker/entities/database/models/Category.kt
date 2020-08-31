package mx.kinich49.itemtracker.entities.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categories")
data class Category(
    @PrimaryKey
    @ColumnInfo(name = "mobile_id") val mobileId: Long? = null,
    @ColumnInfo(name = "remote_id") val remoteId: Long? = null,
    val name: String,
    var state: Int = 0
)