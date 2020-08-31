package mx.kinich49.itemtracker.entities.apis.models

import java.time.LocalDate

data class ShoppingListResponse(
    val remoteId: Long,
    val mobileId: Long? = null,
    val shoppingDate: LocalDate,
    val store: StoreResponse,
    val shoppingItems: List<ShoppingItemResponse>
)

data class ShoppingItemResponse(
    val remoteId: Long,
    val mobileId: Long? = null,
    val currency: String,
    val quantity: Double,
    val unit: String,
    val unitPrice: Int,
    val shoppingListId: Long,
    val item: ItemResponse
)

data class ItemResponse(
    val remoteId: Long,
    val mobileId: Long? = null,
    val name: String,
    val brand: BrandResponse?,
    val category: CategoryResponse
)

data class BrandResponse(
    val remoteId: Long,
    val mobileId: Long? = null,
    val name: String
)

data class StoreResponse(
    val remoteId: Long,
    val mobileId: Long? = null,
    val name: String
)

data class CategoryResponse(
    val remoteId: Long,
    val mobileId: Long? = null,
    val name: String
)