package mx.kinich49.itemtracker.entities.usecases.sync.upstream

import io.reactivex.Observable
import mx.kinich49.itemtracker.entities.database.daos.ShoppingItemDao
import mx.kinich49.itemtracker.entities.database.daos.ShoppingListDao
import mx.kinich49.itemtracker.entities.database.models.relationships.CompositeShoppingList

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
            val shoppingLists = shoppingListDao.getPendingShoppingLists()
            shoppingLists
                .forEach {
                    val shoppingItems = shoppingItemDao.getShoppingItemsBy(it.mobileId)
                    it.shoppingItems = shoppingItems
                    emitter.onNext(it)
                }
            emitter.onComplete()
        }
    }
}