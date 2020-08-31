package mx.kinich49.itemtracker.entities.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import mx.kinich49.itemtracker.entities.database.models.Brand

@Dao
interface BrandDao {

    @Query("SELECT * FROM Brands")
    fun getAllBrands(): LiveData<List<Brand>>

    @Query(
        "SELECT * FROM Brands " +
                "WHERE state != 3 " +
                "AND name LIKE  '%' || :name || '%' " +
                "COLLATE NOCASE"
    )
    fun getActiveBrandsLike(name: String): List<Brand>

    @Insert
    fun insert(brand: Brand): Long

    @Insert
    fun insert(vararg brand: Brand)

    @Query("DELETE FROM Brands WHERE mobile_id = :id")
    fun delete(id: Long)

    @Query("UPDATE Brands SET state = 3 WHERE mobile_id = :id")
    fun inactivate(id: Long)
}