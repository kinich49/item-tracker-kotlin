package mx.kinich49.itemtracker.models.sync

import io.reactivex.Observable
import mx.kinich49.itemtracker.models.database.daos.ShoppingItemDao
import mx.kinich49.itemtracker.models.database.daos.ShoppingListDao
import mx.kinich49.itemtracker.models.database.relations.CompositeShoppingList

class LoadPendingShoppingListUseCase(
    private val shoppingListDao: ShoppingListDao,
    private val shoppingItemDao: ShoppingItemDao
) {

    /**
     * Loads ShoppingLists whose state is 1 (Pending to upload)
     * in the encapsulated class {@link CompositeShoppingList]
     * @return an observable of {@link CompositeShoppingList}
     */
    fun execute(): Observable<CompositeShoppingList> {
        return Observable.create { emitter ->
            shoppingListDao.getPendingShoppingLists()
                .forEach {
                    it.shoppingItems = shoppingItemDao.getShoppingItemsBy(it.shoppingListId)
                    emitter.onNext(it)
                }
            emitter.onComplete()
        }
    }
}