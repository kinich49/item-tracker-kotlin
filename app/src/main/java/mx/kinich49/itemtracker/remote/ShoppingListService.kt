package mx.kinich49.itemtracker.remote

import mx.kinich49.itemtracker.models.database.ShoppingList
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface ShoppingListService {

    @GET(value = "shoppingLists")
    fun getShoppingLists(): Single<JsonApi<List<ShoppingList>>>

    @GET(value = "shoppingLists/{id}")
    fun getShoppingListBy(@Path(value = "id") id: Long): Single<JsonApi<ShoppingList>>

    @POST(value = "shoppingLists")
    fun postItem(@Body shoppingList: ShoppingList): Single<JsonApi<ShoppingList>>

    @DELETE(value = "shoppingLists/{id}")
    fun deleteShoppingList(@Path(value = "id") id: Long): Completable
}