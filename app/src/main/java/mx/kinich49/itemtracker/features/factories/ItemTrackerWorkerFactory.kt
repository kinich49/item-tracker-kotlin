package mx.kinich49.itemtracker.features.factories

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import mx.kinich49.itemtracker.entities.usecases.sync.downstream.DownstreamSynUseCase
import mx.kinich49.itemtracker.entities.usecases.sync.downstream.DownstreamSyncWorker
import mx.kinich49.itemtracker.entities.usecases.sync.upstream.ShoppingListSyncUseCase
import mx.kinich49.itemtracker.entities.usecases.sync.upstream.UpstreamSyncWorker

class ItemTrackerWorkerFactory(
    private val shoppingListSyncUseCase: ShoppingListSyncUseCase,
    private val downstreamSync: DownstreamSynUseCase
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