package mx.kinich49.itemtracker.models.sync

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Single

class DownstreamSyncWorker(
    context: Context,
    params: WorkerParameters,
    private val sync: DownstreamSync
) : RxWorker(context, params) {

    override fun createWork(): Single<Result> {
        return sync
            .downloadData()
            .andThen(Single.just(Result.success()))
            .onErrorReturnItem(Result.failure())
    }
}