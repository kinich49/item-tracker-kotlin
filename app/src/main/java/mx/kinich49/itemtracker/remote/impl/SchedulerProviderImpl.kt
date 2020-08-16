package mx.kinich49.itemtracker.remote.impl

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import mx.kinich49.itemtracker.remote.SchedulerProvider

class SchedulerProviderImpl : SchedulerProvider {
    override fun networkScheduler(): Scheduler = Schedulers.io()

    override fun mainScheduler(): Scheduler = AndroidSchedulers.mainThread()
}