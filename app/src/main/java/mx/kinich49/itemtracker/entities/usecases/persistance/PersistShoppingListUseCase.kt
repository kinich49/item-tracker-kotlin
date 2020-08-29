package mx.kinich49.itemtracker.entities.usecases.persistance

import io.reactivex.Completable
import mx.kinich49.itemtracker.entities.database.daos.*
import mx.kinich49.itemtracker.entities.database.models.Item
import mx.kinich49.itemtracker.entities.database.models.ShoppingList
import mx.kinich49.itemtracker.features.shoppingList.extensions.toDatabaseModel
import mx.kinich49.itemtracker.features.shoppingList.models.ShoppingItem
import mx.kinich49.itemtracker.features.shoppingList.models.Store
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
        shoppingItems: List<ShoppingItem>,
        shoppingDate: LocalDate
    ): Completable {

        return Completable.create { emitter ->

            //get current store id if already exists
            //or insert current store and retrieve id
            val storeMobileId = if (store.id == null) {
                store.toDatabaseModel().let {
                    it.state = 1
                    storeDao.insert(it)
                }
            } else {
                store.id!!
            }

            //Create new shopping list
            //and get id
            val shoppingListMobileId: Long = ShoppingList(
                shoppingDate = shoppingDate,
                storeId = storeMobileId,
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

                val brandMobileId = if (brand?.id != null)
                    brand.id
                else if (brand != null && brand.id == null) {
                    brand.toDatabaseModel()
                        .let { b ->
                            b.state = 1
                            brandDao.insert(b)
                        }
                } else null

                //Get category from mediatorLiveData
                //Category is mandatory, set current category id
                //or new id if category does not exists in db
                val category = it.categoryMediator.value
                val categoryMobileId = if (category?.id == null) {
                    category?.toDatabaseModel()!!.let { c ->
                        c.state = 1
                        categoryDao.insert(c)
                    }
                } else category.id

                //Get item from mediatorLiveData
                //Item is mandatory, set current item id
                //or new id if item does not exits in db
                val item = it.itemMediator.value
                val itemMobileId: Long = if (item?.id == null) {
                    Item(
                        name = it.itemName.value!!,
                        brandId = brandMobileId,
                        categoryId = categoryMobileId!!,
                        state = 1
                    )
                        .let { newItem ->
                            newItem.state = 1
                            itemDao.insert(newItem)
                        }
                } else {
                    item.id!!
                }


                it.toDatabaseModel(shoppingListMobileId, itemMobileId, 1)
            }.toTypedArray().let {
                shoppingItemDao.insert(*it)
            }

            emitter.onComplete()
        }
    }

}