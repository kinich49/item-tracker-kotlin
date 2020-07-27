package com.kinich49.itemtracker.models.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kinich49.itemtracker.models.database.Store

@Dao
interface StoreDao {

    @Query("SELECT * FROM Stores")
    fun getAllBrands(): List<Store>

    @Insert
    fun insert(store: Store)

    @Insert
    fun insert(vararg store: Store)
}