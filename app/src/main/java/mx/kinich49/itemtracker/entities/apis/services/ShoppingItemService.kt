package mx.kinich49.itemtracker.entities.apis.services

import mx.kinich49.itemtracker.entities.database.models.ShoppingItem
import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.entities.apis.models.JsonApi
import retrofit2.http.*

interface ShoppingItemService {
    @GET(value = "shoppingItems")
    fun getShoppingItems(): Single<JsonApi<List<ShoppingItem>>>

    @GET(value = "shoppingItems/{id}")
    fun getShoppingItemBy(@Path(value = "id") id: Long): Single<JsonApi<ShoppingItem>>

    @POST(value = "shoppingItems")
    fun postItem(@Body store: ShoppingItem): Single<JsonApi<ShoppingItem>>

    @DELETE(value = "shoppingItems/{id}")
    fun deleteShoppingItem(@Path(value = "id") id: Long): Completable
}