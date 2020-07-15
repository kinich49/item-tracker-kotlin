package com.kinich49.itemtracker.models.daos

import androidx.room.Dao
import androidx.room.Query
import com.kinich49.itemtracker.models.Item

@Dao
interface ItemDao {

    @Query("SELECT * FROM Items")
    fun getAllBrands(): List<Item>
}