package mx.kinich49.itemtracker.models.view

import java.time.LocalDate

data class ShoppingList(
    var id: Long? = null,
    var shoppingDate: LocalDate?,
    var store: Store?,
    val shoppingItems: MutableList<ShoppingItem> = ArrayList()
)