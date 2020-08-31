package mx.kinich49.itemtracker.entities.apis.services

import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.entities.apis.models.CategoryRequest
import mx.kinich49.itemtracker.entities.apis.models.CategoryResponse
import mx.kinich49.itemtracker.entities.apis.models.JsonApi
import retrofit2.http.*

interface CategoryService {

    @GET(value = "categories")
    fun getCategories(): Single<JsonApi<List<CategoryResponse>>>

    @GET(value = "categories/{id}")
    fun getCategoryBy(@Path(value = "id") id: Long): Single<JsonApi<CategoryResponse>>

    @POST(value = "categories")
    fun postCategory(@Body category: CategoryRequest): Single<JsonApi<CategoryResponse>>

    @DELETE(value = "categories/{id}")
    fun deleteCategory(@Path(value = "id") id: Long): Completable
}