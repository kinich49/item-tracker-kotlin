package mx.kinich49.itemtracker.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import mx.kinich49.itemtracker.LiveEvent

class HomeViewModel : ViewModel() {

    private val _addListEvent: LiveEvent<Unit> = LiveEvent()
    val addListEvent: LiveData<Unit> = _addListEvent

    fun onAddShoppingListClick() {
        _addListEvent.call()
    }
}