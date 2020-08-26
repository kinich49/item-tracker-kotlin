package mx.kinich49.itemtracker.entities.apis.services

import mx.kinich49.itemtracker.entities.database.models.ShoppingList
import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.entities.apis.models.JsonApi
import mx.kinich49.itemtracker.entities.apis.models.ShoppingListRequest
import mx.kinich49.itemtracker.entities.apis.models.ShoppingListResponse
import retrofit2.http.*

interface ShoppingListService {

    @GET(value = "shoppingLists")
    fun getShoppingLists():
            Single<JsonApi<List<ShoppingList>>>

    @GET(value = "shoppingLists/{id}")
    fun getShoppingListBy(@Path(value = "id") id: Long):
            Single<JsonApi<ShoppingList>>

    @POST(value = "shoppingLists")
    fun postShoppingList(@Body shoppingList: ShoppingListRequest):
            Single<JsonApi<ShoppingListResponse>>

    @DELETE(value = "shoppingLists/{id}")
    fun deleteShoppingList(@Path(value = "id") id: Long): Completable
}