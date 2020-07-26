package com.kinich49.itemtracker.shoppigList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kinich49.itemtracker.models.view.*
import timber.log.Timber

class ShoppingListViewModel : ViewModel() {

    private var nextShoppingItemId = -1L
    private val _data: MutableLiveData<MutableList<RecyclerItem>> = MutableLiveData()
    val data: LiveData<MutableList<RecyclerItem>> = _data
    private val _store: MutableLiveData<Store> = MutableLiveData()
    var store: LiveData<Store> = _store
    private val _storeError: MutableLiveData<String> = MutableLiveData()
    val storeError: LiveData<String> = _storeError
    private val _shoppingDateError: MutableLiveData<String> = MutableLiveData()
    val shoppingDateError: LiveData<String> = _shoppingDateError

    init {
        val items = ArrayList<RecyclerItem>()
        _data.value = items
        _store.value = Store()
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
        val store = _store.value

        if (store?.name.isNullOrBlank() || store?.name.isNullOrEmpty()) {
            _storeError.value = "Store can't be empty"
        }
    }

}