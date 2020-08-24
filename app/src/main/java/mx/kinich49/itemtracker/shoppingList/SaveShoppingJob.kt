package mx.kinich49.itemtracker.shoppingList

import mx.kinich49.itemtracker.models.view.ShoppingItem
import mx.kinich49.itemtracker.models.view.Store
import io.reactivex.Completable
import mx.kinich49.itemtracker.models.view.ShoppingItemViewModel
import java.time.LocalDate

interface SaveShoppingJob {

    @Throws(IllegalArgumentException::class)
    fun persistLocally(
        store: Store, shoppingItems: List<ShoppingItemViewModel>,
        shoppingDate: LocalDate
    ): Completable
}