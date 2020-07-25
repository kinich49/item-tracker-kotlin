package com.kinich49.itemtracker.models.view

import java.time.LocalDate

data class ShoppingList(
    val id: Long,
    var shoppingDate: LocalDate?,
    var store: Store?,
    val shoppingItems: MutableList<ShoppingItem> = ArrayList()
)