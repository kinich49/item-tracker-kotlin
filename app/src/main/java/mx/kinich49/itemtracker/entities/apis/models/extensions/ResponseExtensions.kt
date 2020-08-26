package mx.kinich49.itemtracker.entities.apis.models.extensions

import mx.kinich49.itemtracker.entities.apis.models.*
import mx.kinich49.itemtracker.entities.database.models.*

fun StoreResponse.toDBModel(): Store {
    return Store(this.id, this.name)
}

fun BrandResponse.toDBModel(): Brand {
    return Brand(this.id, this.name)
}

fun CategoryResponse.toDBModel(): Category {
    return Category(this.id, this.name)
}

fun ItemResponse.toDBModel(): Item {
    return Item(this.id, this.name, this.brand?.id, this.category.id)
}

fun ShoppingItemResponse.toDBModel(): ShoppingItem {
    return ShoppingItem(
        this.id, this.quantity, this.unit,
        this.unitPrice, this.shoppingListId,
        this.item.id, this.currency
    )
}

fun ShoppingListResponse.toDBModel(): ShoppingList {
    return ShoppingList(this.id, this.shoppingDate, this.store.id)
}