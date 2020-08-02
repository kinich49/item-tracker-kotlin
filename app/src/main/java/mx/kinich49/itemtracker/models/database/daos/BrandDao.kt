package mx.kinich49.itemtracker.models.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import mx.kinich49.itemtracker.models.database.Brand
import mx.kinich49.itemtracker.models.database.relations.BrandWithItems

@Dao
interface BrandDao {

    @Query("SELECT * FROM Brands")
    fun getAllBrands(): LiveData<List<Brand>>

    @Query("SELECT * FROM Brands WHERE name LIKE  '%' || :name || '%' COLLATE NOCASE")
    fun getBrandsLike(name: String): List<Brand>

    @Transaction
    @Query("SELECT * FROM Brands")
    fun getAllBrandsWithItems(): LiveData<List<BrandWithItems>>

    @Insert
    fun insert(brand: Brand): Long

    @Insert
    fun insert(vararg brand: Brand)
}