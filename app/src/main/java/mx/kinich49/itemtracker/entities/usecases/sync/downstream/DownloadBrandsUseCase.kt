package mx.kinich49.itemtracker.entities.usecases.sync.downstream

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.internal.operators.completable.CompletableDefer
import mx.kinich49.itemtracker.entities.apis.extensions.toDBModel
import mx.kinich49.itemtracker.entities.apis.models.BrandResponse
import mx.kinich49.itemtracker.entities.apis.services.BrandService
import mx.kinich49.itemtracker.entities.database.daos.BrandDao
import mx.kinich49.itemtracker.entities.database.models.Brand

class DownloadBrandsUseCase(
    private val brandService: BrandService,
    private val brandDao: BrandDao
) {

    fun execute(): Completable {
        return downloadBrands()
            .flatMap { brandResponses ->
                Single.defer {
                    mapResponseToDatabaseModel(brandResponses)
                }
            }
            .flatMapCompletable { brands ->
                CompletableDefer.defer {
                    persistBrands(brands)
                }
            }
    }

    private fun downloadBrands(): Single<List<BrandResponse>> {
        return brandService.getBrands()
            .flatMap {
                Single.just(it.data)
            }
    }

    private fun mapResponseToDatabaseModel(response: List<BrandResponse>): Single<List<Brand>> {
        return Single.create { emitter ->
            if (!response.isNullOrEmpty()) {
                val dbModels =  response.map {
                    it.toDBModel()
                }
                emitter.onSuccess(dbModels)
            } else {
                emitter.onError(RuntimeException("List was null"))
            }
        }
    }

    private fun persistBrands(brands: List<Brand>): Completable {
        return Completable.create { emitter ->
            brands.toTypedArray()
                .let {
                    brandDao.insert(*it)
                }
            emitter.onComplete()
        }
    }
}