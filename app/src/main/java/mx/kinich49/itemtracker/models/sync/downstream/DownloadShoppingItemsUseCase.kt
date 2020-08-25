package mx.kinich49.itemtracker.models.sync.downstream

import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.models.database.ShoppingItem
import mx.kinich49.itemtracker.models.database.daos.ShoppingItemDao
import mx.kinich49.itemtracker.remote.ShoppingItemService

class DownloadShoppingItemsUseCase(
    private val shoppingItemService: ShoppingItemService,
    private val shoppingItemDao: ShoppingItemDao
) {

    fun execute(): Completable {
        return downloadShoppingItems()
            .flatMapCompletable { shoppingItems ->
                Completable.defer {
                    persistShoppingItems(shoppingItems)
                }
            }
    }

    private fun downloadShoppingItems(): Single<List<ShoppingItem>> {
        return shoppingItemService.getShoppingItems()
            .flatMap {
                Single.just(it.data)
            }
    }

    private fun persistShoppingItems(shoppingItems: List<ShoppingItem>): Completable {
        return Completable.create { emitter ->
            shoppingItems.toTypedArray()
                .let {
                    shoppingItemDao.insert(*it)
                }
            emitter.onComplete()
        }
    }
}