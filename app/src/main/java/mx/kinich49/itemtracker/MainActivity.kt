package mx.kinich49.itemtracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mx.kinich49.itemtracker.remote.SchedulerProvider
import mx.kinich49.itemtracker.shoppingList.ItemTrackerViewModelFactory
import mx.kinich49.itemtracker.shoppingList.impl.SaveShoppingJobImpl

class MainActivity : AppCompatActivity() {

    private val shoppingListFactory by lazy {
        val db = ItemTrackerDatabase.getDatabase(applicationContext)
        val saveShoppingJob = SaveShoppingJobImpl(
            db.shoppingListDao(),
            db.storeDao(), db.shoppingItemDao(), db.brandDao(), db.categoryDao(),
            db.itemDao()
        )
        ItemTrackerViewModelFactory(saveShoppingJob, SchedulerProvider.DEFAULT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = ItemTrackerFragmentFactory(shoppingListFactory)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        App.getDownloadSync(this)
//            .downloadData()
//            .subscribeOn(SchedulerProvider.DEFAULT.networkScheduler())
//            .observeOn(SchedulerProvider.DEFAULT.mainScheduler())
//            .subscribe(object : CompletableObserver {
//                override fun onComplete() {
//                    Timber.d("On Complete")
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onError(e: Throwable) {
//                }
//
//            })
    }
}