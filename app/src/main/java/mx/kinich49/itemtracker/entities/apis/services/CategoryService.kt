package mx.kinich49.itemtracker.entities.apis.services

import mx.kinich49.itemtracker.entities.database.models.Category
import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.entities.apis.models.JsonApi
import retrofit2.http.*

interface CategoryService {

    @GET(value = "categories")
    fun getCategories(): Single<JsonApi<List<Category>>>

    @GET(value = "categories/{id}")
    fun getCategoryBy(@Path(value = "id") id: Long): Single<JsonApi<Category>>

    @POST(value = "categories")
    fun postCategory(@Body category: Category): Single<JsonApi<Category>>

    @DELETE(value = "categories/{id}")
    fun deleteCategory(@Path(value = "id") id: Long): Completable
}