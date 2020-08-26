package mx.kinich49.itemtracker.entities.apis.services

import io.reactivex.Scheduler
import mx.kinich49.itemtracker.entities.apis.models.SchedulerProviderImpl


interface SchedulerProvider {

    fun networkScheduler(): Scheduler

    fun mainScheduler(): Scheduler

    companion object {
        val DEFAULT: SchedulerProvider = SchedulerProviderImpl()
    }
}