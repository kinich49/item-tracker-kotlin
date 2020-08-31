package mx.kinich49.itemtracker.entities.usecases.sync.downstream

import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.entities.apis.extensions.toDBModel
import mx.kinich49.itemtracker.entities.apis.models.ShoppingListResponse
import mx.kinich49.itemtracker.entities.apis.services.ShoppingListService
import mx.kinich49.itemtracker.entities.database.daos.ShoppingListDao
import mx.kinich49.itemtracker.entities.database.models.ShoppingList

class DownloadShoppingListsUseCase(
    private val shoppingListService: ShoppingListService,
    private val shoppingListDao: ShoppingListDao
) {


    fun execute(): Completable {
        return downloadShoppingLists()
            .flatMap { shoppingListResponses ->
                Single.defer { mapResponseToDatabaseModel(shoppingListResponses) }
            }
            .flatMapCompletable { shoppingLists ->
                Completable.defer {
                    persistShoppingLists(shoppingLists)
                }
            }
    }

    private fun downloadShoppingLists(): Single<List<ShoppingListResponse>> {
        return shoppingListService.getShoppingLists()
            .flatMap {
                Single.just(it.data)
            }
    }

    private fun mapResponseToDatabaseModel(response: List<ShoppingListResponse>): Single<List<ShoppingList>> {
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