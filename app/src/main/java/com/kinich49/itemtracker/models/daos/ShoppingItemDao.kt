package com.kinich49.itemtracker.models.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kinich49.itemtracker.models.ShoppingItem

@Dao
interface ShoppingItemDao {

    @Query("SELECT * FROM Shopping_Items")
    fun getAllShoppingItems(): LiveData<List<ShoppingItem>>

    @Insert
    fun insert(shoppingItem: ShoppingItem)
}