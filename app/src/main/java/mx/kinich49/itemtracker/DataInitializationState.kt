package mx.kinich49.itemtracker

import androidx.annotation.StringRes

sealed class DataInitializationState(val canAddData: Boolean) {
    data class Error(
        val errorMessage: String? = null
    ) : DataInitializationState(false)

    object Unchecked : DataInitializationState(false)
}

sealed class Success : DataInitializationState(true) {
    object DataDownloaded : Success()
    object NoData : Success()
    object NoInfo : Success()
}

sealed class InProgress : DataInitializationState(false) {
    data class Enqueued(@StringRes val message: Int = R.string.waiting_internet_connection) :
        InProgress()

    data class Downloading(@StringRes val message: Int = R.string.sync_in_progress) :
        InProgress()
}