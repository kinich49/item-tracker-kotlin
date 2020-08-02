package mx.kinich49.itemtracker.shoppigList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.kinich49.itemtracker.LiveEvent
import mx.kinich49.itemtracker.models.view.RecyclerItem
import mx.kinich49.itemtracker.models.view.ShoppingItem
import mx.kinich49.itemtracker.models.view.Store
import mx.kinich49.itemtracker.remote.SchedulerProvider
import java.time.LocalDate
import java.time.Month

class ShoppingListViewModel(
    private val saveShoppingJob: SaveShoppingJob
) : ViewModel() {

    private val _data: MutableLiveData<MutableList<RecyclerItem>> = MutableLiveData()
    val data: LiveData<MutableList<RecyclerItem>> = _data

    val store: MutableLiveData<Store> = MutableLiveData()
    private val _storeError: MutableLiveData<String> = MutableLiveData()
    val storeError: LiveData<String> = _storeError

    val shoppingDate: MutableLiveData<LocalDate> = MutableLiveData()
    private val _shoppingDateError: MutableLiveData<String> = MutableLiveData()
    val shoppingDateError: LiveData<String> = _shoppingDateError
    val datePickerEvent: LiveEvent<LocalDate> = LiveEvent()
    val onShoppingComplete: LiveEvent<Unit> = LiveEvent()

    init {
        val items = ArrayList<RecyclerItem>()
        _data.value = items
        store.value = Store()
        shoppingDate.value = LocalDate.of(2020, Month.MAY, 20)
    }

    fun addBlankShoppingItem() {
        val blankShoppingItem = ShoppingItem().toRecyclerItem()

        val items = _data.value
        items?.add(blankShoppingItem)
        _data.value = items
    }

    fun saveList() {
        val store = store.value

        if (store?.name.isNullOrBlank() || store?.name.isNullOrEmpty()) {
            _storeError.value = "Store can't be empty"
        } else {

            val shoppingItems =
                _data.value?.map {
                    it.data as ShoppingItem
                }
            val compositeDisposable = saveShoppingJob.persistLocally(
                store!!,
                shoppingItems!!,
                shoppingDate.value!!
            )
                .subscribeOn(SchedulerProvider.DEFAULT_NETWORK)
                .observeOn(SchedulerProvider.DEFAULT_MAIN)
                .subscribe {
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
}