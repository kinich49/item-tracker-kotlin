package mx.kinich49.itemtracker.remote

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SchedulerProvider {

    companion object {
        val DEFAULT_NETWORK: Scheduler = Schedulers.io()
        val DEFAULT_MAIN: Scheduler = AndroidSchedulers.mainThread()
    }
}