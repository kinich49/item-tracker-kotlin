package mx.kinich49.itemtracker.factories

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import mx.kinich49.itemtracker.models.sync.DownstreamSync
import mx.kinich49.itemtracker.models.sync.DownstreamSyncWorker
import mx.kinich49.itemtracker.models.sync.ShoppingListSyncUseCase
import mx.kinich49.itemtracker.models.sync.UpstreamSyncWorker

class ItemTrackerWorkerFactory(
    private val shoppingListSyncUseCase: ShoppingListSyncUseCase,
    private val downstreamSync: DownstreamSync
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when (workerClassName) {
            UpstreamSyncWorker::class.java.name ->
                UpstreamSyncWorker(appContext, workerParameters, shoppingListSyncUseCase)

            DownstreamSyncWorker::class.java.name ->
                DownstreamSyncWorker(appContext, workerParameters, downstreamSync)
            else -> null
        }

    }
}