package mx.kinich49.itemtracker.remote

import mx.kinich49.itemtracker.models.database.Category
import io.reactivex.Completable
import io.reactivex.Single
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