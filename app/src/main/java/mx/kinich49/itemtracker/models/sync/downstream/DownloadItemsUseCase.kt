package mx.kinich49.itemtracker.models.sync.downstream

import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.models.database.Item
import mx.kinich49.itemtracker.models.database.daos.ItemDao
import mx.kinich49.itemtracker.remote.ItemService

class DownloadItemsUseCase(
    private val itemService: ItemService,
    private val itemDao: ItemDao
) {

    fun execute(): Completable {
        return downloadItems()
            .flatMapCompletable { items ->
                Completable.defer {
                    persistItems(items)
                }
            }
    }

    private fun downloadItems(): Single<List<Item>> {
        return itemService.getItems()
            .flatMap {
                Single.just(it.data)
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