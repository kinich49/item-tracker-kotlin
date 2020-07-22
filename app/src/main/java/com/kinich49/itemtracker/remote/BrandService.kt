package com.kinich49.itemtracker.remote

import com.kinich49.itemtracker.models.Brand
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface BrandService {

    @GET(value = "brands")
    fun getBrands(): Observable<JsonApi<List<Brand>>>

    @GET(value = "brands/{id}")
    fun getBrandBy(@Path(value = "id") id: Long): Observable<JsonApi<Brand>>

    @POST(value = "brands")
    fun postBrand(@Body brand: Brand): Observable<JsonApi<Brand>>

    @DELETE(value = "brands/{id}")
    fun deleteBrand(@Path(value = "id") id: Long): Completable
}