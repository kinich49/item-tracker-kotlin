package mx.kinich49.itemtracker.entities.database.extensions

import mx.kinich49.itemtracker.entities.apis.models.*
import mx.kinich49.itemtracker.entities.database.models.Brand
import mx.kinich49.itemtracker.entities.database.models.Category
import mx.kinich49.itemtracker.entities.database.models.Store
import mx.kinich49.itemtracker.entities.database.models.relationships.CompositeItem
import mx.kinich49.itemtracker.entities.database.models.relationships.CompositeShoppingItem
import mx.kinich49.itemtracker.entities.database.models.relationships.CompositeShoppingList
import mx.kinich49.itemtracker.features.shoppingList.models.Item

fun Brand.toView(): mx.kinich49.itemtracker.features.shoppingList.models.Brand =
    mx.kinich49.itemtracker.features.shoppingList.models.Brand(this.mobileId, this.name)

fun Category.toView(): mx.kinich49.itemtracker.features.shoppingList.models.Category =
    mx.kinich49.itemtracker.features.shoppingList.models.Category(this.mobileId, this.name)

fun Store.toView(): mx.kinich49.itemtracker.features.shoppingList.models.Store =
    mx.kinich49.itemtracker.features.shoppingList.models.Store(this.mobileId, this.name)

fun CompositeItem.toView(): Item {
    val brand = this.brand?.toView()
    val category = this.category.toView()
    return Item(this.item.mobileId, brand, category, this.item.name)
}

fun CompositeShoppingList.toRequest(): ShoppingListRequest {
    require(!this.shoppingItems.isNullOrEmpty())
    return ShoppingListRequest(
        this.shoppingDate,
        this.store.toRequest(),
        this.shoppingItems!!.map { it.toRequest() }, this.mobileId
    )
}

fun Store.toRequest(): StoreRequest {
    val id: Long? = if (this.state == 1) null else this.remoteId
    val mobileId: Long? = if (this.state == 1) this.mobileId else null
    return StoreRequest(id, this.name, mobileId)
}

fun CompositeShoppingItem.toRequest(): ShoppingItemRequest {
    val shoppingItemMobileId = this.shoppingItemMobileId
    val itemRemoteId = if (this.itemState == 1) null else this.itemRemoteId
    val mobileItemId = if (this.itemState == 1) this.itemMobileId else null
    val brandRequest = this.brand?.toRequest()
    val categoryRequest = this.category.toRequest()

    return ShoppingItemRequest(
        itemRemoteId, shoppingItemMobileId, this.unit, this.unitPrice,
        this.quantity, this.currency, this.name, brandRequest,
        categoryRequest, mobileItemId
    )
}

fun Brand.toRequest(): BrandRequest {
    val remoteId: Long? = if (this.state == 1) null else this.mobileId
    val mobileId: Long? = if (this.state == 1) this.mobileId else null

    return BrandRequest(remoteId, this.name, mobileId)
}

fun Category.toRequest(): CategoryRequest {
    val remoteId: Long? = if (this.state == 1) null else this.mobileId
    val mobileId: Long? = if (this.state == 1) this.mobileId else null

    return CategoryRequest(remoteId, this.name, mobileId)

}
