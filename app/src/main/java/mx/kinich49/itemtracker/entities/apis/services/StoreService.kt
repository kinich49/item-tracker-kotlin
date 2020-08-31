package mx.kinich49.itemtracker.entities.apis.services

import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.entities.apis.models.JsonApi
import mx.kinich49.itemtracker.entities.apis.models.StoreRequest
import mx.kinich49.itemtracker.entities.apis.models.StoreResponse
import retrofit2.http.*

interface StoreService {

    @GET(value = "stores")
    fun getStores(): Single<JsonApi<List<StoreResponse>>>

    @GET(value = "stores/{id}")
    fun getStoreBy(@Path(value = "id") id: Long): Single<JsonApi<StoreResponse>>

    @POST(value = "stores")
    fun postItem(@Body store: StoreRequest): Single<JsonApi<StoreResponse>>

    @DELETE(value = "stores/{id}")
    fun deleteStore(@Path(value = "id") id: Long): Completable
}