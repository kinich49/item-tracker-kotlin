package mx.kinich49.itemtracker.features.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import mx.kinich49.itemtracker.features.home.HomeViewModel
import mx.kinich49.itemtracker.entities.apis.services.SchedulerProvider
import mx.kinich49.itemtracker.entities.usecases.persistance.PersistShoppingListUseCase
import mx.kinich49.itemtracker.features.shoppingList.ShoppingListViewModel

class ItemTrackerViewModelFactory(
    private val persistShoppingList: PersistShoppingListUseCase,
    private val schedulerProvider: SchedulerProvider,
    private val workManager: WorkManager
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return when {
            modelClass.isAssignableFrom(ShoppingListViewModel::class.java)
            -> ShoppingListViewModel(persistShoppingList, schedulerProvider, workManager) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java)
            -> HomeViewModel(workManager) as T
            else -> throw IllegalArgumentException("Class $modelClass not implemented")
        }
    }

}