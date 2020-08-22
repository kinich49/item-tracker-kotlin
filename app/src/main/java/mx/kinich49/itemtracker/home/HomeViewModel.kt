package mx.kinich49.itemtracker.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.work.*
import mx.kinich49.itemtracker.DataInitializationState
import mx.kinich49.itemtracker.InProgress
import mx.kinich49.itemtracker.LiveEvent
import mx.kinich49.itemtracker.Success
import mx.kinich49.itemtracker.models.sync.DownstreamSyncWorker

class HomeViewModel(private val workManager: WorkManager) : ViewModel() {

    private val _addListEvent: LiveEvent<Unit> = LiveEvent()
    val addListEvent: LiveData<Unit> = _addListEvent
    private val _dataInitState =
        MutableLiveData<DataInitializationState>(DataInitializationState.Unchecked)
    val dataInitState: LiveData<DataInitializationState> = _dataInitState

    private val dataInitObserver = Observer<List<WorkInfo>> { workInfo ->
        if (!workInfo.isNullOrEmpty()) {
            when (workInfo[0].state) {
                WorkInfo.State.SUCCEEDED -> {
                    _dataInitState.value = Success.DataDownloaded
                }

                WorkInfo.State.ENQUEUED, WorkInfo.State.BLOCKED -> {
                    _dataInitState.value = InProgress.Enqueued()
                }

                WorkInfo.State.RUNNING -> {
                    _dataInitState.value = InProgress.Downloading()
                }

                WorkInfo.State.CANCELLED -> {
                    _dataInitState.value = Success.NoData
                }

                WorkInfo.State.FAILED -> {
                    _dataInitState.value = DataInitializationState.Error()
                }
            }
        }


    }

    init {
        workManager
            .getWorkInfosForUniqueWorkLiveData("Init Local Database")
            .observeForever(dataInitObserver)
    }

    fun onAddShoppingListClick() {
        _addListEvent.call()
    }

    fun onCancelEnqueuedInitialization() {
        workManager.cancelUniqueWork("Init Local Database")
    }

    fun onRetryFailedInitialization() {
        val workRequest = OneTimeWorkRequestBuilder<DownstreamSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        workManager
            .enqueueUniqueWork(
                "Init Local Database",
                ExistingWorkPolicy.REPLACE, workRequest
            )
    }

    override fun onCleared() {
        workManager
            .getWorkInfosForUniqueWorkLiveData("Init Local Database")
            .removeObserver(dataInitObserver)
    }
}