package mx.kinich49.itemtracker.models.sync.upstream

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Single

class UpstreamSyncWorker(
    context: Context, workerParams: WorkerParameters,
    private val syncUseCase: ShoppingListSyncUseCase
) :
    RxWorker(context, workerParams) {

    override fun createWork(): Single<Result> {
        return syncUseCase
            .execute()
            .andThen(Single.just(Result.success()))
            .onErrorReturnItem(Result.failure())
    }
}