package mx.kinich49.itemtracker.models.sync

import io.reactivex.Completable
import mx.kinich49.itemtracker.models.database.daos.*
import mx.kinich49.itemtracker.remote.response.ShoppingListResponse
import mx.kinich49.itemtracker.remote.response.toDBModel

class UpdateLocalShoppingListUseCase(
    private val storeDao: StoreDao,
    private val shoppingListDao: ShoppingListDao,
    private val brandDao: BrandDao,
    private val categoryDao: CategoryDao,
    private val itemDao: ItemDao,
    private val shoppingItemDao: ShoppingItemDao
) {

    fun execute(response: ShoppingListResponse): Completable {
        return Completable.create { emitter ->
            response.store
                .takeIf {
                    it.mobileId != null
                }
                ?.apply {
                    storeDao.inactivate(this.mobileId!!)
                }
                ?.toDBModel()
                ?.let {
                    storeDao.insert(it)
                }

            response
                .apply {
                    shoppingListDao.inactivate(this.mobileId!!)
                }
                .toDBModel()
                .let {
                    shoppingListDao.insert(it)
                }

            for (shoppingItem in response.shoppingItems) {
                val item = shoppingItem.item

                item.brand
                    ?.takeIf {
                        it.mobileId != null
                    }
                    ?.apply {
                        brandDao.inactivate(this.mobileId!!)
                    }
                    ?.toDBModel()
                    ?.let {
                        brandDao.insert(it)
                    }

                item.category
                    .takeIf {
                        it.mobileId != null
                    }
                    ?.apply {
                        categoryDao.inactivate(this.mobileId!!)
                    }
                    ?.toDBModel()
                    ?.let {
                        categoryDao.insert(it)
                    }

                item
                    .takeIf {
                        it.mobileId != null
                    }
                    ?.apply {
                        itemDao.inactivate(this.mobileId!!)
                    }
                    ?.toDBModel()
                    ?.let {
                        itemDao.insert(it)
                    }

                shoppingItem
                    .apply {
                        shoppingItemDao.inactivate(this.mobileId!!)
                    }
                    .toDBModel()
                    .let {
                        shoppingItemDao.insert(it)
                    }
            }

            emitter.onComplete()
        }
    }
}