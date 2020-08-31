package mx.kinich49.itemtracker.entities.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "Shopping_Lists",
    foreignKeys = [
        ForeignKey(
            entity = Store::class,
            parentColumns = arrayOf("mobile_id"),
            childColumns = arrayOf("store_id")
        )]
)
data class ShoppingList(
    @PrimaryKey @ColumnInfo(name = "mobile_id") val mobileId: Long? = null,
    @ColumnInfo(name = "remote_id") val remoteId: Long? = null,
    @ColumnInfo(name = "shopping_date") val shoppingDate: LocalDate,
    @ColumnInfo(name = "store_id") val storeId: Long,
    var state: Int = 0
)