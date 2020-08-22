package mx.kinich49.itemtracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.*
import mx.kinich49.itemtracker.models.database.*
import mx.kinich49.itemtracker.models.database.converters.Converters
import mx.kinich49.itemtracker.models.database.daos.*
import mx.kinich49.itemtracker.models.sync.DownstreamSyncWorker
import timber.log.Timber

@Database(
    entities = [Brand::class,
        Category::class,
        Item::class,
        ShoppingItem::class,
        ShoppingList::class,
        Store::class,
        User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ItemTrackerDatabase : RoomDatabase() {

    abstract fun brandDao(): BrandDao
    abstract fun categoryDao(): CategoryDao
    abstract fun itemDao(): ItemDao
    abstract fun storeDao(): StoreDao
    abstract fun shoppingItemDao(): ShoppingItemDao
    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: ItemTrackerDatabase? = null

        fun getDatabase(context: Context): ItemTrackerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemTrackerDatabase::class.java,
                    "item_tracker.db"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            Timber.tag("TEST").d("onCreate")
                            val workRequest = OneTimeWorkRequestBuilder<DownstreamSyncWorker>()
                                .setConstraints(
                                    Constraints.Builder()
                                        .setRequiredNetworkType(NetworkType.CONNECTED)
                                        .build()
                                )
                                .build()

                            WorkManager.getInstance(context)
                                .enqueueUniqueWork(
                                    "Init Local Database",
                                    ExistingWorkPolicy.KEEP, workRequest
                                )
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            Timber.tag("TEST").d("onOpen")
                        }
                    })
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}