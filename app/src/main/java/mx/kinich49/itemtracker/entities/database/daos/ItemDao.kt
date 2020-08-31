package mx.kinich49.itemtracker.entities.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import mx.kinich49.itemtracker.entities.database.models.Item
import mx.kinich49.itemtracker.entities.database.models.relationships.CompositeItem

@Dao
interface ItemDao {

    @Query(
        "SELECT it.mobile_id as item_mobile_id, it.remote_id as item_remote_id, " +
                "it.state as item_state, it.name as item_name, " +
                "it.brand_id as item_brand_id, it.category_id as item_category_id, " +
                "br.mobile_id as brand_mobile_id, br.remote_id as brand_remote_id, " +
                "br.name as brand_name, br.state as brand_state, " +
                "ca.mobile_id as category_mobile_id, ca.remote_id as category_remote_id, " +
                "ca.name as category_name, ca.state as category_state " +
                "FROM Items as it " +
                "INNER JOIN Brands br " +
                "ON br.mobile_id = it.brand_id " +
                "INNER JOIN Categories ca " +
                "ON ca.mobile_id = it.category_id"
    )
    fun getAllItems(): List<CompositeItem>

    @Query(
        "SELECT it.mobile_id as item_mobile_id, it.remote_id as item_remote_id, " +
                "it.state as item_state, it.name as item_name, " +
                "it.brand_id as item_brand_id, it.category_id as item_category_id, " +
                "br.mobile_id as brand_mobile_id, br.remote_id as brand_remote_id, " +
                "br.name as brand_name, br.state as brand_state, " +
                "ca.mobile_id as category_mobile_id, ca.remote_id as category_remote_id, " +
                "ca.name as category_name, ca.state as category_state " +
                "FROM Items as it " +
                "INNER JOIN Brands br " +
                "ON br.mobile_id = it.brand_id " +
                "INNER JOIN Categories ca " +
                "ON ca.mobile_id = it.category_id " +
                "WHERE it.state != 3 " +
                "AND it.name LIKE  '%' || :name || '%' " +
                "COLLATE NOCASE"
    )
    fun getActiveItemsLike(name: String): List<CompositeItem>

    @Query("SELECT mobile_id from Items WHERE remote_id = :remoteId")
    fun getMobileIdForRemoteId(remoteId: Long): Long

    @Insert
    fun insert(item: Item): Long

    @Insert
    fun insert(vararg item: Item)

    @Query("DELETE FROM Items WHERE mobile_id = :id")
    fun delete(id: Long)

    @Query("UPDATE Items SET state = 3 WHERE mobile_id = :id")
    fun inactivate(id: Long)
}