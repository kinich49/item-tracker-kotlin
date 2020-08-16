package mx.kinich49.itemtracker.remote

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mx.kinich49.itemtracker.remote.impl.SchedulerProviderImpl


interface SchedulerProvider {

    fun networkScheduler(): Scheduler

    fun mainScheduler(): Scheduler

    companion object {
        val DEFAULT: SchedulerProvider = SchedulerProviderImpl()
    }
}