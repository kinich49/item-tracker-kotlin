package com.kinich49.itemtracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kinich49.itemtracker.models.database.*
import com.kinich49.itemtracker.models.database.converters.Converters
import com.kinich49.itemtracker.models.database.Store
import com.kinich49.itemtracker.models.database.daos.*

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
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}