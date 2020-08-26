package mx.kinich49.itemtracker.entities.apis.services

import mx.kinich49.itemtracker.entities.database.models.Brand
import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.entities.apis.models.JsonApi
import retrofit2.http.*

interface BrandService {

    @GET(value = "brands")
    fun getBrands(): Single<JsonApi<List<Brand>>>

    @GET(value = "brands/{id}")
    fun getBrandBy(@Path(value = "id") id: Long): Single<JsonApi<Brand>>

    @POST(value = "brands")
    fun postBrand(@Body brand: Brand): Single<JsonApi<Brand>>

    @DELETE(value = "brands/{id}")
    fun deleteBrand(@Path(value = "id") id: Long): Completable
}