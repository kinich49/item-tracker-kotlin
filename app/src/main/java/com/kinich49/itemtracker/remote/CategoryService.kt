package com.kinich49.itemtracker.remote

import com.kinich49.itemtracker.models.database.Category
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface CategoryService {

    @GET(value = "categories")
    fun getCategories(): Observable<JsonApi<List<Category>>>

    @GET(value = "categories/{id}")
    fun getCategoryBy(@Path(value = "id") id: Long): Observable<JsonApi<Category>>

    @POST(value = "categories")
    fun postCategory(@Body category: Category): Observable<JsonApi<Category>>

    @DELETE(value = "categories/{id}")
    fun deleteCategory(@Path(value = "id") id: Long): Completable
}