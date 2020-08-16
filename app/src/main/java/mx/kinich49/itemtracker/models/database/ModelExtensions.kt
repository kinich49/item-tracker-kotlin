package mx.kinich49.itemtracker.models.database

import mx.kinich49.itemtracker.models.database.relations.CompositeItem
import mx.kinich49.itemtracker.models.database.relations.CompositeShoppingItem
import mx.kinich49.itemtracker.models.database.relations.CompositeShoppingList
import mx.kinich49.itemtracker.models.view.Item
import mx.kinich49.itemtracker.remote.requests.*

fun Brand.toView(): mx.kinich49.itemtracker.models.view.Brand =
    mx.kinich49.itemtracker.models.view.Brand(this.id, this.name)

fun Category.toView(): mx.kinich49.itemtracker.models.view.Category =
    mx.kinich49.itemtracker.models.view.Category(this.id, this.name)

fun Store.toView(): mx.kinich49.itemtracker.models.view.Store =
    mx.kinich49.itemtracker.models.view.Store(this.id, this.name)

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
