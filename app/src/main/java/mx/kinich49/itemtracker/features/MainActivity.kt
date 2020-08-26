package mx.kinich49.itemtracker.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.WorkManager
import mx.kinich49.itemtracker.entities.database.ItemTrackerDatabase
import mx.kinich49.itemtracker.R
import mx.kinich49.itemtracker.features.factories.ItemTrackerFragmentFactory
import mx.kinich49.itemtracker.entities.apis.services.SchedulerProvider
import mx.kinich49.itemtracker.features.factories.ItemTrackerViewModelFactory
import mx.kinich49.itemtracker.entities.usecases.persistance.PersistShoppingListUseCase

class MainActivity : AppCompatActivity() {

    private val shoppingListFactory by lazy {
        val db = ItemTrackerDatabase.getDatabase(applicationContext)
        val saveShoppingJob = PersistShoppingListUseCase(
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

        //Small trick to initialize Database as soon as user opens app
        //DB initialization only happens after a query is executed
        //And we want to check data state before this app is usable
        ItemTrackerDatabase.getDatabase(this)
            .userDao().getUserBy(1L).observe(this,
                Observer {

                })
    }
}