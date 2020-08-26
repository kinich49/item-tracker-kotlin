package mx.kinich49.itemtracker.features.shoppingList.models

data class Store(
    var id: Long? = null,
    var name: String? = null
) {

    override fun toString(): String = name ?: "Store id: $id"

    companion object {
        fun from(store: mx.kinich49.itemtracker.entities.database.models.Store): Store =
            Store(store.id, store.name)
    }
}