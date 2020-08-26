package mx.kinich49.itemtracker.entities.database.extensions

import mx.kinich49.itemtracker.entities.database.models.relationships.CompositeItem
import mx.kinich49.itemtracker.entities.database.models.relationships.CompositeShoppingItem
import mx.kinich49.itemtracker.entities.database.models.relationships.CompositeShoppingList
import mx.kinich49.itemtracker.features.shoppingList.models.Item
import mx.kinich49.itemtracker.entities.apis.models.*
import mx.kinich49.itemtracker.entities.database.models.Brand
import mx.kinich49.itemtracker.entities.database.models.Category
import mx.kinich49.itemtracker.entities.database.models.Store

fun Brand.toView(): mx.kinich49.itemtracker.features.shoppingList.models.Brand =
    mx.kinich49.itemtracker.features.shoppingList.models.Brand(this.id, this.name)

fun Category.toView(): mx.kinich49.itemtracker.features.shoppingList.models.Category =
    mx.kinich49.itemtracker.features.shoppingList.models.Category(this.id, this.name)

fun Store.toView(): mx.kinich49.itemtracker.features.shoppingList.models.Store =
    mx.kinich49.itemtracker.features.shoppingList.models.Store(this.id, this.name)

fun CompositeItem.toView(): Item {
    val brand = this.brand?.toView()
    val category = this.category.toView()
    return Item(this.item.id, brand, category, this.item.name)
}

fun CompositeShoppingList.toRequest(): ShoppingListRequest {
    require(!this.shoppingItems.isNullOrEmpty())
    return ShoppingListRequest(
        this.shoppingDate,
        this.store.toRequest(),
        this.shoppingItems!!.map { it.toRequest() }, this.shoppingListId
    )
}

fun Store.toRequest(): StoreRequest {
    val id: Long? = if (this.state == 1) null else this.id
    val mobileId: Long? = if (this.state == 1) this.id else null
    return StoreRequest(id, this.name, mobileId)
}

fun CompositeShoppingItem.toRequest(): ShoppingItemRequest {
    val mobileId = this.shoppingItemId
    val itemId = if (this.itemState == 1) null else this.itemId
    val mobileItemId = if (this.itemState == 1) this.itemId else null
    val brandRequest = this.brand?.toRequest()
    val categoryRequest = this.category.toRequest()

    return ShoppingItemRequest(
        itemId, this.unit, this.unitPrice,
        this.quantity, this.currency, this.name, brandRequest,
        categoryRequest, mobileItemId, mobileId
    )
}

fun Brand.toRequest(): BrandRequest {
    val id: Long? = if (this.state == 1) null else this.id
    val mobileId: Long? = if (this.state == 1) this.id else null

    return BrandRequest(id, this.name, mobileId)
}

fun Category.toRequest(): CategoryRequest {
    val id: Long? = if (this.state == 1) null else this.id
    val mobileId: Long? = if (this.state == 1) this.id else null

    return CategoryRequest(id, this.name, mobileId)

}
