package mx.kinich49.itemtracker.models.sync.downstream

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Single

class DownstreamSyncWorker(
    context: Context,
    params: WorkerParameters,
    private val sync: DownstreamSynUseCase
) : RxWorker(context, params) {

    override fun createWork(): Single<Result> {
        return sync
            .execute()
            .andThen(Single.just(Result.success()))
            .onErrorReturnItem(Result.failure())
    }
}