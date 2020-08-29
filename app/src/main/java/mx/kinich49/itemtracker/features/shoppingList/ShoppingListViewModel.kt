package mx.kinich49.itemtracker.features.shoppingList

import androidx.lifecycle.*
import androidx.work.*
import mx.kinich49.itemtracker.core.LiveEvent
import mx.kinich49.itemtracker.entities.apis.services.SchedulerProvider
import mx.kinich49.itemtracker.entities.usecases.persistance.PersistShoppingListUseCase
import mx.kinich49.itemtracker.entities.usecases.sync.upstream.UpstreamSyncWorker
import mx.kinich49.itemtracker.features.shoppingList.models.RecyclerItem
import mx.kinich49.itemtracker.features.shoppingList.models.ShoppingItem
import mx.kinich49.itemtracker.features.shoppingList.models.Store
import java.time.LocalDate

class ShoppingListViewModel(
    private val persistShoppingList: PersistShoppingListUseCase,
    private val schedulerProvider: SchedulerProvider,
    private val workManager: WorkManager
) : ViewModel() {

    private val _shoppingItems: MutableLiveData<MutableList<RecyclerItem>> =
        MutableLiveData(ArrayList(listOf(ShoppingItem().toRecyclerItem())))
    val shoppingItems: LiveData<MutableList<RecyclerItem>> = _shoppingItems

    val store = MutableLiveData<Store>(Store())
    val storeName = MutableLiveData<String>()
    private val storeMediator = MediatorLiveData<Store>()
    private val _storeError: MutableLiveData<String> = MutableLiveData()
    val storeError: LiveData<String> = _storeError

    val shoppingDate: MutableLiveData<LocalDate> = MutableLiveData(LocalDate.now())
    val datePickerEvent: LiveEvent<LocalDate> = LiveEvent()
    val onShoppingComplete: LiveEvent<Unit> = LiveEvent()

    private var mediatorStoreObserver = Observer<Store> {
        //Empty observer to activate storeMediator
    }

    init {
        storeMediator.addSource(store) { value ->
            storeMediator.value = value
        }

        storeMediator.addSource(storeName) { value ->
            val mediatorData = storeMediator.value
            mediatorData?.name = value
            mediatorData?.id = null
            _storeError.value = null
            storeMediator.value = mediatorData
        }

        storeMediator.observeForever(mediatorStoreObserver)
    }

    fun addBlankShoppingItem() {
        val blankShoppingItem = ShoppingItem().toRecyclerItem()

        val items = _shoppingItems.value
        items?.add(blankShoppingItem)
        _shoppingItems.value = items
    }

    fun saveList() {
        val store = storeMediator.value

        if (store?.name.isNullOrBlank() || store?.name.isNullOrEmpty()) {
            _storeError.value = "Store can't be empty"
        } else {
            val shoppingItems =
                _shoppingItems.value?.map {
                    it.data as ShoppingItem
                }
            val compositeDisposable = persistShoppingList.execute(
                store!!,
                shoppingItems!!,
                shoppingDate.value!!
            )
                .subscribeOn(schedulerProvider.networkScheduler())
                .observeOn(schedulerProvider.mainScheduler())
                .subscribe {
                    val workRequest = OneTimeWorkRequestBuilder<UpstreamSyncWorker>()
                        .setConstraints(
                            Constraints.Builder()
                                .setRequiredNetworkType(NetworkType.CONNECTED)
                                .build()
                        )
                        .build()

                    workManager
                        .enqueueUniqueWork(
                            "Upstream Sync Work", ExistingWorkPolicy.KEEP,
                            workRequest
                        )
                    onShoppingComplete.call()
                }
        }

    }

    fun onShoppingDateClick() {
        datePickerEvent.value = shoppingDate.value
    }

    fun onShoppingDateSelected(value: LocalDate) {
        shoppingDate.value = value
    }

    override fun onCleared() {
        super.onCleared()
        storeMediator.removeObserver(mediatorStoreObserver)

    }
}