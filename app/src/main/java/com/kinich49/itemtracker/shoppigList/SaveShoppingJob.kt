package com.kinich49.itemtracker.shoppigList

import com.kinich49.itemtracker.models.view.ShoppingItem
import com.kinich49.itemtracker.models.view.Store
import io.reactivex.Completable
import java.time.LocalDate

interface SaveShoppingJob {

    @Throws(IllegalArgumentException::class)
    fun persistLocally(
        store: Store, shoppingItems: List<ShoppingItem>,
        shoppingDate: LocalDate
    ): Completable
}