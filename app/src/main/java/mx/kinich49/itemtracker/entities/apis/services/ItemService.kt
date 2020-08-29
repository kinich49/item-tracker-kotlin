package mx.kinich49.itemtracker.entities.apis.services

import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.entities.apis.models.ItemResponse
import mx.kinich49.itemtracker.entities.apis.models.JsonApi
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ItemService {
    @GET(value = "items")
    fun getItems(): Single<JsonApi<List<ItemResponse>>>

    @GET(value = "items/{id}")
    fun getItemBy(@Path(value = "id") id: Long): Single<JsonApi<ItemResponse>>

    @DELETE(value = "items/{id}")
    fun deleteItem(@Path(value = "id") id: Long): Completable
}