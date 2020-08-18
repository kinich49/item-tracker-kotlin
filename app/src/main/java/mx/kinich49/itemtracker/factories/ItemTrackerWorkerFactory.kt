package mx.kinich49.itemtracker.factories

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import mx.kinich49.itemtracker.models.sync.SyncWorker
import mx.kinich49.itemtracker.models.sync.UpstreamSync

class ItemTrackerWorkerFactory(private val upstreamSync: UpstreamSync) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when (workerClassName) {
            SyncWorker::class.java.name ->
                SyncWorker(appContext, workerParameters, upstreamSync)
            else -> null
        }

    }
}