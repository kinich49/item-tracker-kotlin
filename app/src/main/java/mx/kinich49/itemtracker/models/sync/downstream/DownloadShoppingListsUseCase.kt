package mx.kinich49.itemtracker.models.sync.downstream

import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.models.database.ShoppingList
import mx.kinich49.itemtracker.models.database.daos.ShoppingListDao
import mx.kinich49.itemtracker.remote.ShoppingListService

class DownloadShoppingListsUseCase(
    private val shoppingListService: ShoppingListService,
    private val shoppingListDao: ShoppingListDao
) {


    fun execute(): Completable {
        return downloadShoppingLists()
            .flatMapCompletable { shoppingLists ->
                Completable.defer {
                    persistShoppingLists(shoppingLists)
                }
            }
    }

    private fun downloadShoppingLists(): Single<List<ShoppingList>> {
        return shoppingListService.getShoppingLists()
            .flatMap {
                Single.just(it.data)
            }
    }

    private fun persistShoppingLists(shoppingLists: List<ShoppingList>): Completable {
        return Completable.create { emitter ->
            shoppingLists.toTypedArray()
                .let {
                    shoppingListDao.insert(*it)
                }
            emitter.onComplete()
        }
    }
}