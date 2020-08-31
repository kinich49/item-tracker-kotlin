package mx.kinich49.itemtracker.entities.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import mx.kinich49.itemtracker.entities.database.models.Store

@Dao
interface StoreDao {

    @Query("SELECT * FROM Stores WHERE mobile_id = :id")
    fun getStore(id: Long): Store?

    @Query("SELECT * FROM Stores")
    fun getAllStores(): List<Store>

    @Query(
        "SELECT * FROM Stores " +
                "WHERE state != 3 " +
                "AND name LIKE  '%' || :name || '%' COLLATE NOCASE"
    )
    fun getActiveStoresLike(name: String): List<Store>

    @Insert
    fun insert(store: Store): Long

    @Insert
    fun insert(vararg store: Store)

    @Query("DELETE FROM Stores WHERE mobile_id = :id")
    fun delete(id: Long)

    @Query("UPDATE Stores SET state = 3 WHERE mobile_id = :id")
    fun inactivate(id: Long)
}