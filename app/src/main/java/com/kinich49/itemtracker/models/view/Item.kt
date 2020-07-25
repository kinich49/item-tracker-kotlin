package com.kinich49.itemtracker.models.view

data class Item(
    val id: Long,
    var brand: Brand?,
    var category: Category,
    var name: String?
)