package mx.kinich49.itemtracker.entities.database.models.relationships

import androidx.room.ColumnInfo
import androidx.room.Embedded
import mx.kinich49.itemtracker.entities.database.models.Brand
import mx.kinich49.itemtracker.entities.database.models.Category

data class CompositeShoppingItem(
    @ColumnInfo(name = "shopping_item_mobile_id") val shoppingItemMobileId: Long,
    @ColumnInfo(name = "shopping_item_remote_id") val shoppingItemRemoteId: Long,
    @ColumnInfo(name = "item_mobile_id") val itemMobileId: Long,
    @ColumnInfo(name = "item_remote_id") val itemRemoteId: Long,
    @ColumnInfo(name = "item_state") val itemState: Int,
    val name: String,
    @ColumnInfo(name = "unit_price") val unitPrice: Int,
    val currency: String,
    val quantity: Double,
    val unit: String,
    @Embedded(prefix = "brand_") val brand: Brand?,
    @Embedded(prefix = "category_") val category: Category
)