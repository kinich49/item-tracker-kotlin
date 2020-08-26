package mx.kinich49.itemtracker.entities.usecases.sync.downstream

import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.entities.database.models.Store
import mx.kinich49.itemtracker.entities.database.daos.StoreDao
import mx.kinich49.itemtracker.entities.apis.services.StoreService

class DownloadStoresUseCase(
    private val storeService: StoreService,
    private val storeDao: StoreDao
) {

    fun execute(): Completable {
        return downloadStores()
            .flatMapCompletable { stores ->
                Completable.defer {
                    persistStores(stores)
                }
            }
    }

    private fun downloadStores(): Single<List<Store>> {
        return storeService.getStores()
            .flatMap {
                Single.just(it.data)
            }
    }

    private fun persistStores(stores: List<Store>): Completable {
        return Completable.create { emitter ->
            stores.toTypedArray()
                .let {
                    storeDao.insert(*it)
                }
            emitter.onComplete()
        }
    }
}