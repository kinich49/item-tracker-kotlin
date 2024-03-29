package mx.kinich49.itemtracker.features.shoppingList.models

data class Item(
    var id: Long? = null,
    var brand: Brand? = null,
    var category: Category? = null,
    var name: String? = null
) {

    override fun toString(): String = name ?: "Item id: $id"

}