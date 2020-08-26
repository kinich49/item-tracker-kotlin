package mx.kinich49.itemtracker.entities.apis.services

import mx.kinich49.itemtracker.entities.database.models.Item
import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.entities.apis.models.JsonApi
import retrofit2.http.*

interface ItemService {
    @GET(value = "items")
    fun getItems(): Single<JsonApi<List<Item>>>

    @GET(value = "items/{id}")
    fun getItemBy(@Path(value = "id") id: Long): Single<JsonApi<Item>>

    @POST(value = "items")
    fun postItem(@Body item: Item): Single<JsonApi<Item>>

    @DELETE(value = "items/{id}")
    fun deleteItem(@Path(value = "id") id: Long): Completable
}