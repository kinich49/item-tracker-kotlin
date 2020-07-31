package com.kinich49.itemtracker.models.view

data class Category(
    var id: Long? = null,
    var name: String? = null


) {
    override fun toString(): String = name ?: "Category id: $id"

    companion object {
        fun from(category: com.kinich49.itemtracker.models.database.Category): Category =
            Category(category.id, category.name)
    }
}