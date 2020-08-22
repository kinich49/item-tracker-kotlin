package mx.kinich49.itemtracker.models.sync

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Single

class UpstreamSyncWorker(
    context: Context, workerParams: WorkerParameters,
    private val sync: UpstreamSync
) :
    RxWorker(context, workerParams) {

    override fun createWork(): Single<Result> {
        return sync
            .uploadData()
            .andThen(Single.just(Result.success()))
            .onErrorReturnItem(Result.failure())
    }
}