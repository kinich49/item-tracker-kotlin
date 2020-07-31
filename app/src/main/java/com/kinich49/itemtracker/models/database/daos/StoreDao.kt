package com.kinich49.itemtracker.models.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kinich49.itemtracker.models.database.Brand
import com.kinich49.itemtracker.models.database.Store

@Dao
interface StoreDao {

    @Query("SELECT * FROM Stores")
    fun getAllStores(): List<Store>

    @Query("SELECT * FROM Stores WHERE name LIKE  '%' || :name || '%'")
    fun getStoresLike(name: String): List<Store>

    @Insert
    fun insert(store: Store)

    @Insert
    fun insert(vararg store: Store)
}