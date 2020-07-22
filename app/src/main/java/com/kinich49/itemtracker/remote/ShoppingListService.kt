package com.kinich49.itemtracker.remote

import com.kinich49.itemtracker.models.ShoppingList
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface ShoppingListService {

    @GET(value = "shoppingLists")
    fun getShoppingLists(): Observable<JsonApi<List<ShoppingList>>>

    @GET(value = "shoppingLists/{id}")
    fun getShoppingListBy(@Path(value = "id") id: Long): Observable<JsonApi<ShoppingList>>

    @POST(value = "shoppingLists")
    fun postItem(@Body shoppingList: ShoppingList): Observable<JsonApi<ShoppingList>>

    @DELETE(value = "shoppingLists/{id}")
    fun deleteShoppingList(@Path(value = "id") id: Long): Completable
}