package com.kinich49.itemtracker.shoppigList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kinich49.itemtracker.LiveEvent
import com.kinich49.itemtracker.models.view.*
import timber.log.Timber
import java.time.LocalDate
import java.time.Month

class ShoppingListViewModel : ViewModel() {

    private var nextShoppingItemId = -1L
    private val _data: MutableLiveData<MutableList<RecyclerItem>> = MutableLiveData()
    val data: LiveData<MutableList<RecyclerItem>> = _data

    val store: MutableLiveData<Store> = MutableLiveData()
    private val _storeError: MutableLiveData<String> = MutableLiveData()
    val storeError: LiveData<String> = _storeError

    val shoppingDate: MutableLiveData<LocalDate> = MutableLiveData()
    private val _shoppingDateError: MutableLiveData<String> = MutableLiveData()
    val shoppingDateError: LiveData<String> = _shoppingDateError
    val datePickerEvent: LiveEvent<LocalDate> = LiveEvent()

    init {
        val items = ArrayList<RecyclerItem>()
        _data.value = items
        store.value = Store()
        shoppingDate.value = LocalDate.of(2020, Month.MAY, 20)
    }

    fun addBlankShoppingItem() {
        val blankShoppingItem = ShoppingItem(
            nextShoppingItemId,
            name = "Item $nextShoppingItemId",
            brand = Brand(),
            category = Category()
        ).toRecyclerItem()
        nextShoppingItemId -= 1

        val items = _data.value
        items?.add(blankShoppingItem)
        _data.value = items
    }

    fun saveList() {
        val store = store.value

        if (store?.name.isNullOrBlank() || store?.name.isNullOrEmpty()) {
            _storeError.value = "Store can't be empty"
        }
    }

    fun onShoppingDateClick() {
        datePickerEvent.value = shoppingDate.value
    }

    fun onShoppingDateSelected(value: LocalDate) {
        shoppingDate.value = value
    }
}