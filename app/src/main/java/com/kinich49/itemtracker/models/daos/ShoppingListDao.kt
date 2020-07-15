package com.kinich49.itemtracker.models.daos

import androidx.room.Dao
import androidx.room.Query
import com.kinich49.itemtracker.models.ShoppingList

@Dao
interface ShoppingListDao {

    @Query("SELECT * FROM Shopping_Lists")
    fun getAllBrands(): List<ShoppingList>
}