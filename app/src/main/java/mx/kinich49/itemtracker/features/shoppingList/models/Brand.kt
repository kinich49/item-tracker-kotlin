package mx.kinich49.itemtracker.features.shoppingList.models

data class Brand(
    var id: Long? = null,
    var name: String? = null


) {

    override fun toString(): String = name ?: "Brand id: $id"

}