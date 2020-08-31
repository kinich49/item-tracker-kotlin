package mx.kinich49.itemtracker.entities.apis.services

import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.entities.apis.models.BrandRequest
import mx.kinich49.itemtracker.entities.apis.models.BrandResponse
import mx.kinich49.itemtracker.entities.apis.models.JsonApi
import retrofit2.http.*

interface BrandService {

    @GET(value = "brands")
    fun getBrands(): Single<JsonApi<List<BrandResponse>>>

    @GET(value = "brands/{id}")
    fun getBrandBy(@Path(value = "id") id: Long): Single<JsonApi<BrandResponse>>

    @POST(value = "brands")
    fun postBrand(@Body brand: BrandRequest): Single<JsonApi<BrandResponse>>

    @DELETE(value = "brands/{id}")
    fun deleteBrand(@Path(value = "id") id: Long): Completable
}