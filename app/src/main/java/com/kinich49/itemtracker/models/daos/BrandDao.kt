package com.kinich49.itemtracker.models.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.kinich49.itemtracker.models.Brand

@Dao
interface BrandDao {

    @Query("SELECT * FROM Brands")
    fun getAllBrands(): LiveData<List<Brand>>
}