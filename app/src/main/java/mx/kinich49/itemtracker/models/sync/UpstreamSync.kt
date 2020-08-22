package mx.kinich49.itemtracker.models.sync

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import mx.kinich49.itemtracker.models.database.daos.*
import mx.kinich49.itemtracker.models.database.relations.CompositeShoppingList
import mx.kinich49.itemtracker.models.database.toRequest
import mx.kinich49.itemtracker.remote.ShoppingListService
import mx.kinich49.itemtracker.remote.requests.ShoppingListRequest
import mx.kinich49.itemtracker.remote.response.ShoppingListResponse
import mx.kinich49.itemtracker.remote.response.toDBModel

class UpstreamSync(
    private val shoppingListDao: ShoppingListDao,
    private val shoppingItemDao: ShoppingItemDao,
    private val brandDao: BrandDao,
    private val categoryDao: CategoryDao,
    private val storeDao: StoreDao,
    private val itemDao: ItemDao,
    private val shoppingListService: ShoppingListService
) {

    fun uploadData(): Completable {
        return loadPendingShoppingLists()
            .flatMapSingle { list -> Single.defer { uploadPendingShoppingList(list) } }
            .flatMapCompletable { response -> Completable.defer { updateLocalDB(response) } }
    }

    /**
     * Loads ShoppingLists whose state is 1 (Pending to upload)
     * in the encapsulated class {@link CompositeShoppingList]
     * @return an observable of {@link CompositeShoppingList}
     */
    fun loadPendingShoppingLists(): Observable<CompositeShoppingList> {
        return Observable.create { emitter ->
            shoppingListDao.getPendingShoppingLists()
                .forEach {
                    it.shoppingItems = shoppingItemDao.getShoppingItemsBy(it.shoppingListId)
                    emitter.onNext(it)
                }
            emitter.onComplete()
        }
    }

    /**
     * Receives a compositeShoppingList,
     * and sends it to remote.
     *
     * @return a Single containing the remote response
     */
    fun uploadPendingShoppingList(shoppingList: CompositeShoppingList)
            : Single<ShoppingListResponse> {
        return Single.defer { mapEntityToRequest(shoppingList) }
            .flatMap { request ->
                Single.defer { shoppingListService.postShoppingList(request) }
            }
            .flatMap { response -> Single.just(response.data) }
    }

    /**
     *  Receives a CompositeShoppingList and
     *  transforms it to ShoppingListRequest
     */
    fun mapEntityToRequest(shoppingList: CompositeShoppingList):
            Single<ShoppingListRequest> {
        return Single.create { emitter ->
            emitter.onSuccess(shoppingList.toRequest())
        }
    }

    fun updateLocalDB(response: ShoppingListResponse): Completable {
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