package mx.kinich49.itemtracker.entities.database.models.relationships

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Ignore
import mx.kinich49.itemtracker.entities.database.models.Store
import java.time.LocalDate

data class CompositeShoppingList(
    @ColumnInfo(name = "shopping_list_id") val shoppingListId: Long,
    @ColumnInfo(name = "shopping_date") val shoppingDate: LocalDate,
    @Embedded(prefix = "store_") val store: Store
    ) {
    @Ignore
    var shoppingItems: List<CompositeShoppingItem>? = null
}

