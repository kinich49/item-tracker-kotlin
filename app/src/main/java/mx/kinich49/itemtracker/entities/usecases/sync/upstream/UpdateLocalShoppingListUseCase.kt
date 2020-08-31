package mx.kinich49.itemtracker.entities.usecases.sync.upstream

import io.reactivex.Completable
import mx.kinich49.itemtracker.core.Either
import mx.kinich49.itemtracker.entities.apis.extensions.toDBModel
import mx.kinich49.itemtracker.entities.apis.models.ItemResponse
import mx.kinich49.itemtracker.entities.apis.models.ShoppingListResponse
import mx.kinich49.itemtracker.entities.database.daos.*

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

            val shoppingListId: Long = response
                .apply {
                    shoppingListDao.inactivate(this.mobileId!!)
                }
                .toDBModel()
                .let {
                    shoppingListDao.insert(it)
                }

            for (shoppingItemResponse in response.shoppingItems) {
                val itemResponse = shoppingItemResponse.item

                itemResponse.brand
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

                itemResponse.category
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

                val eitherPairOrLatestItemId = inactiveItemAndGetCurrentMobileId(itemResponse)

                shoppingItemResponse
                    .apply {
                        shoppingItemDao.inactivate(this.mobileId!!)
                        eitherPairOrLatestItemId.fold({
                            val oldItemMobileId = it.first
                            val newItemMobileId = it.second
                            shoppingItemDao.updateItemMobileId(oldItemMobileId, newItemMobileId)
                            this.toDBModel(newItemMobileId, shoppingListId)
                                .let { shoppingItem ->
                                    shoppingItemDao.insert(shoppingItem)
                                }
                        }, {
                            this.toDBModel(it, shoppingListId)
                                .let { shoppingItem ->
                                    shoppingItemDao.insert(shoppingItem)
                                }
                        })
                    }
            }

            emitter.onComplete()
        }
    }

    private fun inactiveItemAndGetCurrentMobileId(itemResponse: ItemResponse):
            Either<Pair<Long, Long>, Long> {
        return if (itemResponse.mobileId != null) {
            itemResponse.apply {
                itemDao.inactivate(this.mobileId!!)
            }
                .toDBModel()
                .let {
                    val newItemMobileId = itemDao.insert(it)
                    Either.Left(Pair(itemResponse.mobileId, newItemMobileId))
                }
        } else {
            val mobileId = itemDao.getMobileIdForRemoteId(itemResponse.remoteId)
            Either.Right(mobileId)
        }
    }

}