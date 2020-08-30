package mx.kinich49.itemtracker.entities.usecases.sync.downstream

import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.entities.apis.extensions.toDBModel
import mx.kinich49.itemtracker.entities.apis.models.ShoppingItemResponse
import mx.kinich49.itemtracker.entities.apis.services.ShoppingItemService
import mx.kinich49.itemtracker.entities.database.daos.ShoppingItemDao
import mx.kinich49.itemtracker.entities.database.models.ShoppingItem

class DownloadShoppingItemsUseCase(
    private val shoppingItemService: ShoppingItemService,
    private val shoppingItemDao: ShoppingItemDao
) {

    fun execute(): Completable {
        return downloadShoppingItems()
            .flatMap { shoppingItemResponse ->
                Single.defer {
                    mapResponseToDatabaseModel(shoppingItemResponse)
                }
            }
            .flatMapCompletable { shoppingItems ->
                Completable.defer {
                    persistShoppingItems(shoppingItems)
                }
            }
    }

    private fun downloadShoppingItems(): Single<List<ShoppingItemResponse>> {
        return shoppingItemService.getShoppingItems()
            .flatMap {
                Single.just(it.data)
            }
    }

    private fun mapResponseToDatabaseModel(response: List<ShoppingItemResponse>): Single<List<ShoppingItem>> {
        return Single.create { emitter ->
            if (!response.isNullOrEmpty()) {
                val dbModels = response.map {
                    it.toDBModel(it.item.remoteId, it.shoppingListId)
                }
                emitter.onSuccess(dbModels)
            } else {
                emitter.onError(RuntimeException("List was null"))
            }
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