package mx.kinich49.itemtracker.entities.usecases.sync.downstream

import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.entities.apis.extensions.toDBModel
import mx.kinich49.itemtracker.entities.apis.models.StoreResponse
import mx.kinich49.itemtracker.entities.apis.services.StoreService
import mx.kinich49.itemtracker.entities.database.daos.StoreDao
import mx.kinich49.itemtracker.entities.database.models.Store

class DownloadStoresUseCase(
    private val storeService: StoreService,
    private val storeDao: StoreDao
) {

    fun execute(): Completable {
        return downloadStores()
            .flatMap { storeResponses ->
                Single.defer {
                    mapResponseToDatabaseModel(storeResponses)
                }
            }
            .flatMapCompletable { stores ->
                Completable.defer {
                    persistStores(stores)
                }
            }
    }

    private fun downloadStores(): Single<List<StoreResponse>> {
        return storeService.getStores()
            .flatMap {
                Single.just(it.data)
            }
    }

    private fun mapResponseToDatabaseModel(response: List<StoreResponse>): Single<List<Store>> {
        return Single.create { emitter ->
            if (!response.isNullOrEmpty()) {
                val dbModels = response.map {
                    it.toDBModel()
                }
                emitter.onSuccess(dbModels)
            } else {
                emitter.onError(RuntimeException("List was null"))
            }
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