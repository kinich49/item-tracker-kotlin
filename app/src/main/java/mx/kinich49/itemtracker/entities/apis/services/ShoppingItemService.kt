package mx.kinich49.itemtracker.entities.apis.services

import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.entities.apis.models.JsonApi
import mx.kinich49.itemtracker.entities.apis.models.ShoppingItemRequest
import mx.kinich49.itemtracker.entities.apis.models.ShoppingItemResponse
import retrofit2.http.*

interface ShoppingItemService {
    @GET(value = "shoppingItems")
    fun getShoppingItems(): Single<JsonApi<List<ShoppingItemResponse>>>

    @GET(value = "shoppingItems/{id}")
    fun getShoppingItemBy(@Path(value = "id") id: Long): Single<JsonApi<ShoppingItemResponse>>

    @POST(value = "shoppingItems")
    fun postItem(@Body shoppingItem: ShoppingItemRequest): Single<JsonApi<ShoppingItemResponse>>

    @DELETE(value = "shoppingItems/{id}")
    fun deleteShoppingItem(@Path(value = "id") id: Long): Completable
}