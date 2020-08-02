package mx.kinich49.itemtracker.remote

import mx.kinich49.itemtracker.models.database.Brand
import io.reactivex.Completable
import io.reactivex.Single
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