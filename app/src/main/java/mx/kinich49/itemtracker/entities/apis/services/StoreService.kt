package mx.kinich49.itemtracker.entities.apis.services

import mx.kinich49.itemtracker.entities.database.models.Store
import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.entities.apis.models.JsonApi
import retrofit2.http.*

interface StoreService {

    @GET(value = "stores")
    fun getStores(): Single<JsonApi<List<Store>>>

    @GET(value = "stores/{id}")
    fun getStoreBy(@Path(value = "id") id: Long): Single<JsonApi<Store>>

    @POST(value = "stores")
    fun postItem(@Body store: Store): Single<JsonApi<Store>>

    @DELETE(value = "stores/{id}")
    fun deleteStore(@Path(value = "id") id: Long): Completable
}