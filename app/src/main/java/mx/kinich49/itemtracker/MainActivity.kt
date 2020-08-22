package mx.kinich49.itemtracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.WorkManager
import mx.kinich49.itemtracker.factories.ItemTrackerFragmentFactory
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
        ItemTrackerViewModelFactory(
            saveShoppingJob, SchedulerProvider.DEFAULT,
            WorkManager.getInstance(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = ItemTrackerFragmentFactory(shoppingListFactory)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ItemTrackerDatabase.getDatabase(this)
            .userDao().getUserBy(1L).observe(this,
                Observer {

                })
    }
}