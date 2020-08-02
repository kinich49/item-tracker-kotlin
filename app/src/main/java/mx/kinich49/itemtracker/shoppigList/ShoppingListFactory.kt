package mx.kinich49.itemtracker.shoppigList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShoppingListFactory(
    private val saveShoppingJob: SaveShoppingJob
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingListViewModel::class.java))
            return ShoppingListViewModel(saveShoppingJob) as T
        throw IllegalArgumentException("Class $modelClass not implemented")
    }

}