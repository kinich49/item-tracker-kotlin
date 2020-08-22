package mx.kinich49.itemtracker.models.sync

import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.models.database.*
import mx.kinich49.itemtracker.models.database.daos.*
import mx.kinich49.itemtracker.remote.*

class DownstreamSync(
    private val brandService: BrandService,
    private val categoryService: CategoryService,
    private val itemService: ItemService,
    private val storeService: StoreService,
    private val shoppingListService: ShoppingListService,
    private val shoppingItemService: ShoppingItemService,
    private val brandDao: BrandDao,
    private val categoryDao: CategoryDao,
    private val itemDao: ItemDao,
    private val storeDao: StoreDao,
    private val shoppingListDao: ShoppingListDao,
    private val shoppingItemDao: ShoppingItemDao
) {

    fun downloadData(): Completable {
        return downloadBrands()
            .flatMapCompletable { data -> persistBrands(data) }
            .andThen(Single.defer { downloadCategories() })
            .flatMapCompletable { data -> persistCategories(data) }
            .andThen(Single.defer { downloadStores() })
            .flatMapCompletable { data -> persistStores(data) }
            .andThen(Single.defer { downloadItems() })
            .flatMapCompletable { data -> persistItems(data) }
    }


    private fun downloadBrands(): Single<List<Brand>> {
        return brandService.getBrands()
            .flatMap {
                Single.just(it.data)
            }
    }

    private fun persistBrands(brands: List<Brand>): Completable {
        return Completable.create { emitter ->
            brands.toTypedArray()
                .let {
                    brandDao.insert(*it)
                }
            emitter.onComplete()
        }
    }

    private fun downloadCategories(): Single<List<Category>> {
        return categoryService.getCategories()
            .flatMap {
                Single.just(it.data)
            }
    }

    private fun persistCategories(categories: List<Category>): Completable {
        return Completable.create { emitter ->
            categories.toTypedArray()
                .let {
                    categoryDao.insert(*it)
                }
            emitter.onComplete()
        }
    }

    private fun downloadStores(): Single<List<Store>> {
        return storeService.getStores()
            .flatMap {
                Single.just(it.data)
            }
    }

    private fun persistStores(stores: List<Store>): Completable {
        return Completable.create { emitter ->
            stores.toTypedArray()
                .let {
                    storeDao.insert(*it)
                }
            emitter.onComplete()
        }
    }

    private fun downloadItems(): Single<List<Item>> {
        return itemService.getItems()
            .flatMap {
                Single.just(it.data)
            }
    }

    private fun persistItems(items: List<Item>): Completable {
        return Completable.create { emitter ->
            items.toTypedArray()
                .let {
                    itemDao.insert(*it)
                }
            emitter.onComplete()
        }
    }

    private fun downloadShoppingLists(): Single<List<ShoppingList>> {
        return shoppingListService.getShoppingLists()
            .flatMap {
                Single.just(it.data)
            }
    }

    private fun persistShoppingLists(shoppingLists: List<ShoppingList>): Completable {
        return Completable.create { emitter ->
            shoppingLists.toTypedArray()
                .let {
                    shoppingListDao.insert(*it)
                }
            emitter.onComplete()
        }
    }

    private fun downloadShoppingItems(): Single<List<ShoppingItem>> {
        return shoppingItemService.getShoppingItems()
            .flatMap {
                Single.just(it.data)
            }
    }

    private fun persistShoppingItems(shoppingItems: List<ShoppingItem>): Completable {
        return Completable.create { emitter ->
            shoppingItems.toTypedArray()
                .let {
                    shoppingItemDao.insert(*it)
                }
            emitter.onComplete()
        }
    }
}