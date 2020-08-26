package mx.kinich49.itemtracker.entities.usecases.sync.downstream

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.internal.operators.completable.CompletableDefer
import mx.kinich49.itemtracker.entities.database.models.Brand
import mx.kinich49.itemtracker.entities.database.daos.BrandDao
import mx.kinich49.itemtracker.entities.apis.services.BrandService

class DownloadBrandsUseCase(
    private val brandService: BrandService,
    private val brandDao: BrandDao
) {

    fun execute(): Completable {
        return downloadBrands()
            .flatMapCompletable { brands ->
                CompletableDefer.defer {
                    persistBrands(brands)
                }
            }
    }

    private fun downloadBrands(): Single<List<Brand>> {
        return brandService.getBrands()
            .flatMap {
                Single.just(it.data)
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