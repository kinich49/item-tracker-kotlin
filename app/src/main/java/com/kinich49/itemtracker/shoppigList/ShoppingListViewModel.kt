package com.kinich49.itemtracker.shoppigList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kinich49.itemtracker.models.view.RecyclerItem
import com.kinich49.itemtracker.models.view.ShoppingItem
import com.kinich49.itemtracker.models.view.Store

class ShoppingListViewModel : ViewModel() {

    private var nextShoppingItemId = -1L
    private val _data: MutableLiveData<MutableList<RecyclerItem>> = MutableLiveData()
    val data: LiveData<MutableList<RecyclerItem>> = _data
    private val _store: MutableLiveData<Store> = MutableLiveData()
    val store: LiveData<Store> = _store

    init {
        val items = ArrayList<RecyclerItem>()
        _data.value = items
        _store.value = Store(-1, "Test Store")
    }

    fun addBlankShoppingItem() {
        val blankShoppingItem = ShoppingItem(
            nextShoppingItemId,
            name = "Item $nextShoppingItemId"
        ).toRecyclerItem()
        nextShoppingItemId -= 1

        val items = _data.value
        items?.add(blankShoppingItem)
        _data.value = items
    }

}