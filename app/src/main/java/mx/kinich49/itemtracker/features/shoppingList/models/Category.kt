package mx.kinich49.itemtracker.features.shoppingList.models

data class Category(
    var id: Long? = null,
    var name: String? = null


) {
    override fun toString(): String = name ?: "Category id: $id"

    companion object {
        fun from(category: mx.kinich49.itemtracker.entities.database.models.Category): Category =
            Category(category.id, category.name)
    }
}