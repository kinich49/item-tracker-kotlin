package mx.kinich49.itemtracker.models.sync

import io.reactivex.Single
import mx.kinich49.itemtracker.models.database.relations.CompositeShoppingList
import mx.kinich49.itemtracker.models.database.toRequest
import mx.kinich49.itemtracker.remote.ShoppingListService
import mx.kinich49.itemtracker.remote.requests.ShoppingListRequest
import mx.kinich49.itemtracker.remote.response.ShoppingListResponse

class UploadPendingShoppingListUseCase(private val shoppingListService: ShoppingListService) {

    /**
     * Receives a compositeShoppingList,
     * and sends it to remote.
     *
     * @return a Single containing the remote response
     */
    fun execute(shoppingList: CompositeShoppingList): Single<ShoppingListResponse> {
        return Single.defer { mapEntityToRequest(shoppingList) }
            .flatMap { request ->
                Single.defer { shoppingListService.postShoppingList(request) }
            }
            .flatMap { response -> Single.just(response.data) }
    }

    /**
     *  Receives a CompositeShoppingList and
     *  transforms it to ShoppingListRequest
     */
    private fun mapEntityToRequest(shoppingList: CompositeShoppingList):
            Single<ShoppingListRequest> {
        return Single.create { emitter ->
            emitter.onSuccess(shoppingList.toRequest())
        }
    }
}