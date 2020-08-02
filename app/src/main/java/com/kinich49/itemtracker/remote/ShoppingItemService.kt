package com.kinich49.itemtracker.remote

import com.kinich49.itemtracker.models.database.ShoppingItem
import io.reactivex.Completable
import io.reactivex.Single
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