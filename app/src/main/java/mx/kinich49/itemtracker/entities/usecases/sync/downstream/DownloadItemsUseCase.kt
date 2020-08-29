package mx.kinich49.itemtracker.entities.usecases.sync.downstream

import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.entities.apis.extensions.toDBModel
import mx.kinich49.itemtracker.entities.apis.models.ItemResponse
import mx.kinich49.itemtracker.entities.apis.services.ItemService
import mx.kinich49.itemtracker.entities.database.daos.ItemDao
import mx.kinich49.itemtracker.entities.database.models.Item

class DownloadItemsUseCase(
    private val itemService: ItemService,
    private val itemDao: ItemDao
) {

    fun execute(): Completable {
        return downloadItems()
            .flatMap { itemResponses ->
                Single.defer {
                    mapResponseToDatabaseModel(itemResponses)
                }
            }
            .flatMapCompletable { items ->
                Completable.defer {
                    persistItems(items)
                }
            }
    }

    private fun downloadItems(): Single<List<ItemResponse>> {
        return itemService.getItems()
            .flatMap {
                Single.just(it.data)
            }
    }

    private fun mapResponseToDatabaseModel(response: List<ItemResponse>): Single<List<Item>> {
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

    private fun persistItems(items: List<Item>): Completable {
        return Completable.create { emitter ->
            items.toTypedArray()
                .let {
                    itemDao.insert(*it)
                }
            emitter.onComplete()
        }
    }
}