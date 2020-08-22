package mx.kinich49.itemtracker.home

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.work.WorkInfo
import androidx.work.WorkManager
import mx.kinich49.itemtracker.DataInitializationState
import mx.kinich49.itemtracker.Initialized
import mx.kinich49.itemtracker.LiveEvent
import mx.kinich49.itemtracker.NotInitialized
import timber.log.Timber

class HomeViewModel(private val workManager: WorkManager) : ViewModel() {

    private val _addListEvent: LiveEvent<Unit> = LiveEvent()
    val addListEvent: LiveData<Unit> = _addListEvent
    val isAddShoppingListEnabled = ObservableBoolean()
    private val _initState = MutableLiveData<DataInitializationState>()
    val dataInitializationState: LiveData<DataInitializationState> = _initState

    private val initObserver = Observer<List<WorkInfo>> { workInfo ->

        Timber.tag("TEST").d("List size: ${workInfo.size}")
        val state: WorkInfo.State? = workInfo.takeIf {
            it.isNotEmpty()
        }?.get(0)?.state

        state?.let {
            Timber.tag("TEST").d("WorkState: ${it.name}")
        }
        when (state) {
            WorkInfo.State.SUCCEEDED -> {
                isAddShoppingListEnabled.set(true)
                _initState.value = Initialized
            }

            WorkInfo.State.ENQUEUED, WorkInfo.State.RUNNING -> {
                isAddShoppingListEnabled.set(false)
                _initState.value = NotInitialized("You need to connect to internet first")
            }
            else -> {
                isAddShoppingListEnabled.set(false)
                _initState.value = NotInitialized("Something went wrong")
            }
        }
    }

    init {
        workManager
            .getWorkInfosForUniqueWorkLiveData("Init Local Database")
            .observeForever(initObserver)
    }

    fun onAddShoppingListClick() {
        _addListEvent.call()
    }

    fun onCheckLocalInitialization() {

    }

    override fun onCleared() {
        workManager
            .getWorkInfosForUniqueWorkLiveData("Init Local Database")
            .removeObserver(initObserver)
    }
}