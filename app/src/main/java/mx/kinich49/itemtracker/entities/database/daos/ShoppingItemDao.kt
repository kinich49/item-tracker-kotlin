package mx.kinich49.itemtracker.entities.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import mx.kinich49.itemtracker.entities.database.models.ShoppingItem
import mx.kinich49.itemtracker.entities.database.models.relationships.CompositeShoppingItem

@Dao
interface ShoppingItemDao {

    @Query("SELECT * FROM Shopping_Items")
    fun getAllShoppingItems(): List<ShoppingItem>

    @Insert
    fun insert(shoppingItem: ShoppingItem)

    @Insert
    fun insert(vararg shoppingItem: ShoppingItem)

    @Query(
        "SELECT si.mobile_id as shopping_item_mobile_id, " +
                "si.remote_id as shopping_item_remote_id, " +
                "si.unit, si.unit_price, si.currency, si.quantity, " +
                "it.mobile_id as item_mobile_id, it.remote_id as item_remote_id, " +
                "it.name as name, it.state as item_state, " +
                "br.mobile_id as brand_mobile_id, br.remote_id as brand_remote_id, " +
                "br.name as brand_name, br.state as brand_state, " +
                "ca.mobile_id as category_mobile_id, ca.remote_id as category_remote_id, " +
                "ca.name as category_name, ca.state as category_state " +
                "FROM Shopping_Items as si " +
                "INNER JOIN Items it " +
                "ON it.mobile_id = si.item_id " +
                "INNER JOIN Brands br " +
                "ON br.mobile_id = it.brand_id " +
                "INNER JOIN Categories ca " +
                "ON ca.mobile_id = it.category_id " +
                "WHERE si.shopping_list_id = :shoppingListId"
    )
    @Transaction
    fun getShoppingItemsBy(shoppingListId: Long): List<CompositeShoppingItem>

    @Transaction
    @Query(
        "UPDATE Shopping_Items SET item_id = :newItemMobileId WHERE item_id = :oldItemMobileId " +
                "AND state = 1"
    )
    fun updateItemMobileId(oldItemMobileId: Long, newItemMobileId: Long)

    @Query("DELETE FROM Shopping_Items WHERE mobile_id = :id")
    fun delete(id: Long)

    @Query("UPDATE Shopping_Items SET state = 3 WHERE mobile_id = :id")
    fun inactivate(id: Long)
}