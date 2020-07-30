package com.kinich49.itemtracker.models.view

data class Item(
    val id: Long,
    var brand: Brand? = null,
    var category: Category? = null,
    var name: String? = null
) {

    override fun toString(): String {
        return name ?: "Item id: $id"
    }
}