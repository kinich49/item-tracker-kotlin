package com.kinich49.itemtracker.models.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kinich49.itemtracker.models.Store

@Dao
interface StoreDao {

    @Query("SELECT * FROM Stores")
    fun getAllBrands(): List<Store>

    @Insert
    fun insert(store: Store)
}