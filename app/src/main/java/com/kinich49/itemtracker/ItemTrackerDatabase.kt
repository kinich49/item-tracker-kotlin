package com.kinich49.itemtracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kinich49.itemtracker.models.*

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
abstract class ItemTrackerDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: ItemTrackerDatabase? = null

        fun getDatabase(context: Context): ItemTrackerDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemTrackerDatabase::class.java,
                    "item_tracker_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}