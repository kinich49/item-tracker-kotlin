package com.kinich49.itemtracker.models.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.kinich49.itemtracker.models.Brand
import com.kinich49.itemtracker.models.relations.BrandWithItems

@Dao
interface BrandDao {

    @Query("SELECT * FROM Brands")
    fun getAllBrands(): LiveData<List<Brand>>

    @Transaction
    @Query("SELECT * FROM Brands")
    fun getAllBrandsWithItems(): LiveData<List<BrandWithItems>>

    @Insert
    fun insert(brand: Brand)
}