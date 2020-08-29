package mx.kinich49.itemtracker.entities.apis.extensions

import mx.kinich49.itemtracker.entities.apis.models.*
import mx.kinich49.itemtracker.entities.database.models.*

fun StoreResponse.toDBModel(): Store {
    return Store(null, this.id, this.name)
}

fun BrandResponse.toDBModel(): Brand {
    return Brand(null, this.id, this.name)
}

fun CategoryResponse.toDBModel(): Category {
    return Category(null, this.id, this.name)
}

fun ItemResponse.toDBModel(): Item {
    return Item(null, this.id, this.name, this.brand?.id, this.category.id)
}

fun ShoppingItemResponse.toDBModel(): ShoppingItem {
    return ShoppingItem(
        null, this.id,
        this.quantity, this.unit,
        this.unitPrice, this.shoppingListId,
        this.item.id, this.currency
    )
}

fun ShoppingListResponse.toDBModel(): ShoppingList {
    return ShoppingList(
        null, this.id,
        this.shoppingDate, this.store.id
    )
}