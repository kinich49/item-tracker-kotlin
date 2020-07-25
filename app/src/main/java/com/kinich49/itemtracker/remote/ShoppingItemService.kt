package com.kinich49.itemtracker.remote

import com.kinich49.itemtracker.models.database.ShoppingItem
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface ShoppingItemService {
    @GET(value = "shoppingItems")
    fun getShoppingItems(): Observable<JsonApi<List<ShoppingItem>>>

    @GET(value = "shoppingItems/{id}")
    fun getShoppingItemBy(@Path(value = "id") id: Long): Observable<JsonApi<ShoppingItem>>

    @POST(value = "shoppingItems")
    fun postItem(@Body store: ShoppingItem): Observable<JsonApi<ShoppingItem>>

    @DELETE(value = "shoppingItems/{id}")
    fun deleteShoppingItem(@Path(value = "id") id: Long): Completable
}