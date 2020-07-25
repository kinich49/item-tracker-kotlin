package com.kinich49.itemtracker.remote

import com.kinich49.itemtracker.models.database.Store
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface StoreService {

    @GET(value = "stores")
    fun getStores(): Observable<JsonApi<List<Store>>>

    @GET(value = "stores/{id}")
    fun getStoreBy(@Path(value = "id") id: Long): Observable<JsonApi<Store>>

    @POST(value = "stores")
    fun postItem(@Body store: Store): Observable<JsonApi<Store>>

    @DELETE(value = "stores/{id}")
    fun deleteStore(@Path(value = "id") id: Long): Completable
}