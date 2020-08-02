package com.kinich49.itemtracker.models.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.kinich49.itemtracker.models.database.Category
import com.kinich49.itemtracker.models.database.relations.CategoryWithItems

@Dao
interface CategoryDao {

    @Query("SELECT * from Categories")
    fun getAllCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM Categories WHERE name LIKE  '%' || :name || '%' COLLATE NOCASE")
    fun getCategoriesLike(name: String): List<Category>

    @Insert
    fun insert(category: Category): Long

    @Insert
    fun insert(vararg category: Category)

    @Transaction
    @Query("SELECT * FROM Categories WHERE id = :id")
    fun getCategoryWithItems(id: Long): LiveData<List<CategoryWithItems>>
}