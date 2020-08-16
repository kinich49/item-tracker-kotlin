package mx.kinich49.itemtracker.models.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import mx.kinich49.itemtracker.models.database.Store
import mx.kinich49.itemtracker.models.database.relations.StoreWithShoppingLists

@Dao
interface StoreDao {

    @Query("SELECT * FROM Stores WHERE id = :id")
    fun getStore(id: Long): Store?

    @Query("SELECT * FROM Stores")
    fun getAllStores(): List<Store>

    @Query("SELECT * FROM Stores WHERE name LIKE  '%' || :name || '%' COLLATE NOCASE")
    fun getStoresLike(name: String): List<Store>

    @Insert
    fun insert(store: Store): Long

    @Insert
    fun insert(vararg store: Store)

    @Query("DELETE FROM Stores WHERE id = :id")
    fun delete(id: Long)

    @Query("UPDATE Stores SET state = 3 WHERE id = :id")
    fun inactivate(id: Long)
}