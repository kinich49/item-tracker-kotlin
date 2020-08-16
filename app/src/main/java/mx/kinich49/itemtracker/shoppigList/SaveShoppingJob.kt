package mx.kinich49.itemtracker.shoppigList

import mx.kinich49.itemtracker.models.view.ShoppingItem
import mx.kinich49.itemtracker.models.view.Store
import io.reactivex.Completable
import java.time.LocalDate

interface SaveShoppingJob {

    @Throws(IllegalArgumentException::class)
    fun persistLocally(
        store: Store, shoppingItems: List<ShoppingItem>,
        shoppingDate: LocalDate
    ): Completable
}