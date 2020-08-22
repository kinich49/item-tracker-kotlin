package mx.kinich49.itemtracker.shoppingList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import mx.kinich49.itemtracker.home.HomeViewModel
import mx.kinich49.itemtracker.remote.SchedulerProvider

class ItemTrackerViewModelFactory(
    private val saveShoppingJob: SaveShoppingJob,
    private val schedulerProvider: SchedulerProvider,
    private val workManager: WorkManager
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return when {
            modelClass.isAssignableFrom(ShoppingListViewModel::class.java)
            -> ShoppingListViewModel(saveShoppingJob, schedulerProvider) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java)
            -> HomeViewModel(workManager) as T
            else -> throw IllegalArgumentException("Class $modelClass not implemented")
        }
    }

}