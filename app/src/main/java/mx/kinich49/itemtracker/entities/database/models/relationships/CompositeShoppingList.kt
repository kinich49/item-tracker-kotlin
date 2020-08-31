package mx.kinich49.itemtracker.entities.database.models.relationships

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Ignore
import mx.kinich49.itemtracker.entities.database.models.Store
import java.time.LocalDate

data class CompositeShoppingList(
    @ColumnInfo(name = "shopping_list_mobile_id") val mobileId: Long,
    @ColumnInfo(name = "shopping_list_remote_id") val remoteId: Long,
    @ColumnInfo(name = "shopping_date") val shoppingDate: LocalDate,
    @Embedded(prefix = "store_") val store: Store
) {
    @Ignore
    var shoppingItems: List<CompositeShoppingItem>? = null
}

