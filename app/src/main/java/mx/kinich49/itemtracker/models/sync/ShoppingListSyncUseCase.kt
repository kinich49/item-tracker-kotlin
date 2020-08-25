package mx.kinich49.itemtracker.models.sync

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import mx.kinich49.itemtracker.models.database.daos.*
import mx.kinich49.itemtracker.models.database.relations.CompositeShoppingList
import mx.kinich49.itemtracker.models.database.toRequest
import mx.kinich49.itemtracker.remote.ShoppingListService
import mx.kinich49.itemtracker.remote.requests.ShoppingListRequest
import mx.kinich49.itemtracker.remote.response.ShoppingListResponse
import mx.kinich49.itemtracker.remote.response.toDBModel

class ShoppingListSyncUseCase(
    private val loadPendingShoppingList: LoadPendingShoppingListUseCase,
    private val uploadPendingShoppingListUseCase: UploadPendingShoppingListUseCase,
    private val updateLocalShoppingList: UpdateLocalShoppingListUseCase
) {

    /**
     * Load all pending shopping lists (state = 1)
     * and uploads to remote one by one.
     * Updates local db when remote responds.
     *
     * Emits onComplete when all pending shopping lists
     * were successfully synced and updated
     *
     */
    fun execute(): Completable {
        return loadPendingShoppingList.execute()
            .flatMapSingle { list ->
                Single.defer {
                    uploadPendingShoppingListUseCase.execute(list)
                }
            }
            .flatMapCompletable { response ->
                Completable.defer {
                    updateLocalShoppingList.execute(
                        response
                    )
                }
            }
    }

}