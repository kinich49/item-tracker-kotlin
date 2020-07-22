package com.kinich49.itemtracker.remote

import com.kinich49.itemtracker.models.Item
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface ItemService {
    @GET(value = "items")
    fun getItems(): Observable<JsonApi<List<Item>>>

    @GET(value = "items/{id}")
    fun getItemBy(@Path(value = "id") id: Long): Observable<JsonApi<Item>>

    @POST(value = "items")
    fun postItem(@Body item: Item): Observable<JsonApi<Item>>

    @DELETE(value = "items/{id}")
    fun deleteItem(@Path(value = "id") id: Long): Completable
}