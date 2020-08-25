package mx.kinich49.itemtracker.models.sync.upstream

import io.reactivex.Completable
import io.reactivex.Single

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