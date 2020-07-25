package com.kinich49.itemtracker.models.view

import com.kinich49.itemtracker.BR
import com.kinich49.itemtracker.R

class ShoppingItem(
    val id: Long,
    var name: String? = null,
    var quantity: String? = null,
    var unitPrice: String? = null,
    val currency: String = "MXN",
    var category: Category? = null,
    var brand: Brand? = null,
    var unit: String = "Unit"
) {

    fun toRecyclerItem() =
        RecyclerItem(
            data = this,
            layoutId = R.layout.blank_shopping_item_layout,
            variableId = BR.shoppingItem
        )
}