package com.kinich49.itemtracker.models.daos

import androidx.room.Dao
import androidx.room.Query
import com.kinich49.itemtracker.models.ShoppingItem

@Dao
interface ShoppingItemDao {

    @Query("SELECT * FROM Shopping_Items")
    fun getAllBrands(): List<ShoppingItem>
}