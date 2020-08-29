package mx.kinich49.itemtracker.entities.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Shopping_Items",
    foreignKeys = [
        ForeignKey(
            entity = Item::class,
            parentColumns = arrayOf("mobile_id"),
            childColumns = arrayOf("item_id")
        ),
        ForeignKey(
            entity = ShoppingList::class,
            parentColumns = arrayOf("mobile_id"),
            childColumns = arrayOf("shopping_list_id")
        )]
)
data class ShoppingItem(
    @PrimaryKey @ColumnInfo(name = "mobile_id") val mobileId: Long? = null,
    @ColumnInfo(name = "remote_id") val remoteId: Long? = null,
    val quantity: Double,
    val unit: String,
    @ColumnInfo(name = "unit_price") val unitPrice: Int,
    @ColumnInfo(name = "shopping_list_id") val shoppingListId: Long,
    @ColumnInfo(name = "item_id") val itemId: Long,
    val currency: String = "MXN",
    var state: Int = 0
)