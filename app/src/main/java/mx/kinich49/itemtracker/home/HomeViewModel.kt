package mx.kinich49.itemtracker.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.work.*
import mx.kinich49.itemtracker.*
import mx.kinich49.itemtracker.R
import mx.kinich49.itemtracker.models.sync.DownstreamSyncWorker
import mx.kinich49.itemtracker.models.sync.UpstreamSyncWorker

class HomeViewModel(private val workManager: WorkManager) : ViewModel() {

    private val _addListEvent: LiveEvent<Unit> = LiveEvent()
    val addListEvent: LiveData<Unit> = _addListEvent

    private val _dataInitState =
        MutableLiveData<DataInitializationState>(DataInitializationState.Unchecked)
    val dataInitState: LiveData<DataInitializationState> = _dataInitState

    private val _upstreamSyncState =
        MutableLiveData<DataInitializationState>(DataInitializationState.Unchecked)
    val upstreamSyncState: LiveData<DataInitializationState> = _upstreamSyncState

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
        } else {
            _dataInitState.value = Success.NoInfo
        }
    }

    private val upstreamSyncObserver = Observer<List<WorkInfo>> { workInfo ->
        if (!workInfo.isNullOrEmpty()) {
            when (workInfo[0].state) {
                WorkInfo.State.SUCCEEDED -> {
                    _upstreamSyncState.value = Success.DataDownloaded
                }

                WorkInfo.State.ENQUEUED, WorkInfo.State.BLOCKED -> {
                    _upstreamSyncState.value = InProgress.Enqueued(R.string.pending_sync)
                }

                WorkInfo.State.RUNNING -> {
                    _upstreamSyncState.value = InProgress.Downloading()
                }

                WorkInfo.State.CANCELLED -> {
                    _upstreamSyncState.value = Success.NoData
                }

                WorkInfo.State.FAILED -> {
                    _upstreamSyncState.value = DataInitializationState.Error()
                }
            }
        } else {
            _dataInitState.value = Success.NoInfo
        }
    }

    init {
        workManager
            .getWorkInfosForUniqueWorkLiveData("Init Local Database")
            .observeForever(dataInitObserver)

        workManager
            .getWorkInfosForUniqueWorkLiveData("Upstream Sync Work")
            .observeForever(upstreamSyncObserver)
    }

    fun onAddShoppingListClick() {
        _addListEvent.call()
    }

    fun onCancelEnqueuedInitialization() {
        workManager.cancelUniqueWork("Init Local Database")
    }

    fun onCancelFailedInitialization() {
        workManager.pruneWork()
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

    fun onRetryFailedUpstreamSync() {
        val workRequest = OneTimeWorkRequestBuilder<UpstreamSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        workManager
            .enqueueUniqueWork(
                "Upstream Sync Work",
                ExistingWorkPolicy.REPLACE, workRequest
            )
    }

    override fun onCleared() {
        workManager
            .getWorkInfosForUniqueWorkLiveData("Init Local Database")
            .removeObserver(dataInitObserver)

        workManager
            .getWorkInfosForUniqueWorkLiveData("Upstream Sync Work")
            .removeObserver(upstreamSyncObserver)
    }
}