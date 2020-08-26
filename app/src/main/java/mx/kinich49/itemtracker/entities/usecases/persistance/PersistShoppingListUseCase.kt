package mx.kinich49.itemtracker.entities.usecases.persistance

import mx.kinich49.itemtracker.entities.database.models.Item
import mx.kinich49.itemtracker.entities.database.models.ShoppingList
import mx.kinich49.itemtracker.entities.database.daos.*
import mx.kinich49.itemtracker.features.shoppingList.models.Store
import mx.kinich49.itemtracker.features.shoppingList.models.toDatabaseModel
import io.reactivex.Completable
import mx.kinich49.itemtracker.features.shoppingList.models.ShoppingItemViewModel
import java.time.LocalDate

class PersistShoppingListUseCase(
    private val shoppingListDao: ShoppingListDao,
    private val storeDao: StoreDao,
    private val shoppingItemDao: ShoppingItemDao,
    private val brandDao: BrandDao,
    private val categoryDao: CategoryDao,
    private val itemDao: ItemDao
) {

    fun execute(
        store: Store,
        shoppingItems: List<ShoppingItemViewModel>,
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
                //Get brand from mediatorLiveData
                //Brand is optional, set null as brandId,
                //or current brand
                //or new id if brand does not exists in db
                val brand = it.brandMediator.value
                val brandId: Long? = brand?.id ?: brand?.toDatabaseModel()?.let { b ->
                    b.state = 1
                    brandDao.insert(b)
                }

                //Get category from mediatorLiveData
                //Category is mandatory, set current category id
                //or new id if category does not exists in db
                val category = it.categoryMediator.value
                val categoryId: Long =
                    category?.id ?: category?.toDatabaseModel()!!.let { c ->
                        c.state = 1
                        categoryDao.insert(c)
                    }

                //Get item from mediatorLiveData
                //Item is mandatory, set current item id
                //or new id if item does not exits in db
                val item = it.itemMediator.value
                val itemId =
                    item?.id ?: Item(
                        name = it.itemName.value!!,
                        brandId = brandId,
                        categoryId = categoryId,
                        state = 1
                    )
                        .let { newItem ->
                            newItem.state = 1
                            itemDao.insert(newItem)
                        }


                it.toDatabaseModel(shoppingListId, itemId, 1)
            }.toTypedArray().let {
                shoppingItemDao.insert(*it)
            }

            emitter.onComplete()
        }
    }

}