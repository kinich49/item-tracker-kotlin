package mx.kinich49.itemtracker.entities.apis.extensions

import mx.kinich49.itemtracker.entities.apis.models.*
import mx.kinich49.itemtracker.entities.database.models.*

fun StoreResponse.toDBModel(): Store {
    return Store(null, this.remoteId, this.name)
}

fun BrandResponse.toDBModel(): Brand {
    return Brand(null, this.remoteId, this.name)
}

fun CategoryResponse.toDBModel(): Category {
    return Category(null, this.remoteId, this.name)
}

fun ItemResponse.toDBModel(): Item {
    return Item(null, this.remoteId, this.name, this.brand?.remoteId, this.category.remoteId)
}

fun ShoppingItemResponse.toDBModel(itemId: Long, shoppingListId: Long): ShoppingItem {
    return ShoppingItem(
        null, this.remoteId,
        this.quantity, this.unit,
        this.unitPrice, shoppingListId,
        itemId, this.currency
    )
}

fun ShoppingListResponse.toDBModel(): ShoppingList {
    return ShoppingList(
        null, this.remoteId,
        this.shoppingDate, this.store.remoteId
    )
}