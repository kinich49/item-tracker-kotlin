package mx.kinich49.itemtracker.entities.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import mx.kinich49.itemtracker.entities.database.models.Category
import mx.kinich49.itemtracker.entities.database.models.relationships.CategoryWithItems

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

    @Query("DELETE FROM Categories WHERE id = :id")
    fun delete(id: Long)

    @Query("UPDATE Categories SET state = 3 WHERE id = :id")
    fun inactivate(id: Long)
}