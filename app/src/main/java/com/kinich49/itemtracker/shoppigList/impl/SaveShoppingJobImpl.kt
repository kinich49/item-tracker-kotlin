package com.kinich49.itemtracker.shoppigList.impl

import com.kinich49.itemtracker.models.database.Item
import com.kinich49.itemtracker.models.database.ShoppingList
import com.kinich49.itemtracker.models.database.daos.*
import com.kinich49.itemtracker.models.view.ShoppingItem
import com.kinich49.itemtracker.models.view.Store
import com.kinich49.itemtracker.models.view.toDatabaseModel
import com.kinich49.itemtracker.shoppigList.SaveShoppingJob
import io.reactivex.Completable
import java.time.LocalDate

class SaveShoppingJobImpl(
    private val shoppingListDao: ShoppingListDao,
    private val storeDao: StoreDao,
    private val shoppingItemDao: ShoppingItemDao,
    private val brandDao: BrandDao,
    private val categoryDao: CategoryDao,
    private val itemDao: ItemDao
) : SaveShoppingJob {

    override fun persistLocally(
        store: Store,
        shoppingItems: List<ShoppingItem>,
        shoppingDate: LocalDate
    ): Completable {

        return Completable.create { emitter ->
            store.toDatabaseModel()

            //get current store id if already exists
            //or insert current store and retrieve id
            val storeId: Long = store.id ?: store.toDatabaseModel().let {
                it.state = 1
                storeDao.insert(it)
            }

            //Create new shopping list
            //and get id
            val shoppingListId: Long = ShoppingList(
                shoppingDate = shoppingDate,
                storeId = storeId,
                state = 1
            ).let {
                shoppingListDao.insert(it)
            }

            shoppingItems.map {
                //Brand is optional, set null as brandId,
                //or current brand
                //or new id if brand does not exists in db
                val brandId: Long? = it.brand?.id ?: it.brand?.toDatabaseModel()?.let { b ->
                    b.state = 1
                    brandDao.insert(b)
                }

                //Category is mandatory, set current category id
                //or new id if category does not exists in db
                val categoryId: Long =
                    it.category?.id ?: it.category?.toDatabaseModel()!!.let { c ->
                        c.state = 1
                        categoryDao.insert(c)
                    }

                //Item is mandatory, set current item id
                //or new id if item does not exits in db
                val itemId =
                    it.item?.id ?: Item(
                        name = it.name!!,
                        brandId = brandId,
                        categoryId = categoryId,
                        state = 1
                    )
                        .let { item ->
                            item.state = 1
                            itemDao.insert(item)
                        }


                it.toDatabaseModel(shoppingListId, itemId, 1)
            }.toTypedArray().let {
                shoppingItemDao.insert(*it)
            }

            emitter.onComplete()
        }
    }

}