package mx.kinich49.itemtracker.entities.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import mx.kinich49.itemtracker.entities.database.models.Category

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

    @Query("DELETE FROM Categories WHERE mobile_id = :id")
    fun delete(id: Long)

    @Query("UPDATE Categories SET state = 3 WHERE mobile_id = :id")
    fun inactivate(id: Long)
}