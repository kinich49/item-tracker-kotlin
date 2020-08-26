package mx.kinich49.itemtracker.features.shoppingList.models

@Throws(IllegalArgumentException::class)
fun Brand.toDatabaseModel(): mx.kinich49.itemtracker.entities.database.models.Brand {
    require(!this.name.isNullOrBlank()) {
        "Brand name must be set"
    }
    return mx.kinich49.itemtracker.entities.database.models.Brand(this.id, this.name!!)
}

@Throws(IllegalArgumentException::class)
fun Category.toDatabaseModel(): mx.kinich49.itemtracker.entities.database.models.Category {
    require(!this.name.isNullOrBlank()) {
        "Category name must be set"
    }
    return mx.kinich49.itemtracker.entities.database.models.Category(this.id, this.name!!)
}

@Throws(IllegalArgumentException::class)
fun Store.toDatabaseModel(): mx.kinich49.itemtracker.entities.database.models.Store {
    require(!this.name.isNullOrBlank()) {
        "Category name must be set"
    }
    return mx.kinich49.itemtracker.entities.database.models.Store(this.id, this.name!!)
}

@Throws(IllegalArgumentException::class)
fun ShoppingItem.toDatabaseModel(
    shoppingListId: Long,
    itemId: Long,
    state: Int = 0
): mx.kinich49.itemtracker.entities.database.models.ShoppingItem {
    require(!this.name.isNullOrBlank()) {
        "Item name must be set"
    }
    require(!this.unitPrice.isBlank()) {
        "Unit price must be set"
    }

    require(!this.quantity.isBlank()) {
        "Quantity must be set"
    }

    return mx.kinich49.itemtracker.entities.database.models.ShoppingItem(
        this.id,
        this.quantity.toDouble(),
        this.unit,
        this.unitPrice.toInt() * 100,
        shoppingListId, itemId, state = state
    )
}

fun ShoppingItemViewModel.toDatabaseModel(
    shoppingListId: Long,
    itemId: Long,
    state: Int = 0
): mx.kinich49.itemtracker.entities.database.models.ShoppingItem {
    val name = this.itemName.value
    require(!name.isNullOrBlank()) {
        "Item name must be set"
    }
    require(!this.unitPrice.isBlank()) {
        "Unit price must be set"
    }

    require(!this.quantity.isBlank()) {
        "Quantity must be set"
    }

    return mx.kinich49.itemtracker.entities.database.models.ShoppingItem(
        null,
        this.quantity.toDouble(),
        this.unit,
        this.unitPrice.toInt() * 100,
        shoppingListId, itemId, state = state
    )
}