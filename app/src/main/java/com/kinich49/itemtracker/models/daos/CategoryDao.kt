package com.kinich49.itemtracker.models.daos

import androidx.room.Dao
import androidx.room.Query
import com.kinich49.itemtracker.models.Category

@Dao
interface CategoryDao {

    @Query("SELECT * from Categories")
    fun getAllCategories(): List<Category>
}