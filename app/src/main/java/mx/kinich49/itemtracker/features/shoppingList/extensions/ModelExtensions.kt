package mx.kinich49.itemtracker.features.shoppingList.extensions

import mx.kinich49.itemtracker.features.shoppingList.models.Brand
import mx.kinich49.itemtracker.features.shoppingList.models.Category
import mx.kinich49.itemtracker.features.shoppingList.models.ShoppingItem
import mx.kinich49.itemtracker.features.shoppingList.models.Store

@Throws(IllegalArgumentException::class)
fun Brand.toDatabaseModel(): mx.kinich49.itemtracker.entities.database.models.Brand {
    require(!this.name.isNullOrBlank()) {
        "Brand name must be set"
    }
    return mx.kinich49.itemtracker.entities.database.models.Brand(
        mobileId = this.id,
        name = this.name!!
    )
}

@Throws(IllegalArgumentException::class)
fun Category.toDatabaseModel(): mx.kinich49.itemtracker.entities.database.models.Category {
    require(!this.name.isNullOrBlank()) {
        "Category name must be set"
    }
    return mx.kinich49.itemtracker.entities.database.models.Category(
        mobileId = this.id,
        name = this.name!!
    )
}

@Throws(IllegalArgumentException::class)
fun Store.toDatabaseModel(): mx.kinich49.itemtracker.entities.database.models.Store {
    require(!this.name.isNullOrBlank()) {
        "Category name must be set"
    }
    return mx.kinich49.itemtracker.entities.database.models.Store(
        mobileId = this.id,
        name = this.name!!
    )
}

fun ShoppingItem.toDatabaseModel(
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
        quantity = this.quantity.toDouble(),
        unit = this.unit,
        unitPrice = this.unitPrice.toInt() * 100,
        shoppingListId = shoppingListId,
        itemId = itemId,
        state = state
    )
}