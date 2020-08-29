package mx.kinich49.itemtracker.entities.apis.models

import java.time.LocalDate

data class ShoppingListRequest(
    val shoppingDate: LocalDate,
    val store: StoreRequest,
    val shoppingItems: List<ShoppingItemRequest>,
    val mobileId: Long?
)

data class ShoppingItemRequest(
    val id: Long?,
    val shoppingItemMobileId: Long,
    val unit: String,
    val unitPrice: Int,
    val quantity: Double,
    val currency: String,
    val name: String,
    val brand: BrandRequest?,
    val category: CategoryRequest,
    val mobileItemId: Long?
)

data class BrandRequest(val id: Long?, val name: String, val mobileId: Long?)

data class StoreRequest(val id: Long?, val name: String, val mobileId: Long?)

data class CategoryRequest(val id: Long?, val name: String, val mobileId: Long?)