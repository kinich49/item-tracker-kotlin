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
        "SELECT si.id as shopping_item_id, si.unit, " +
                "si.unit_price, si.currency, si.quantity, " +
                "it.id as item_id, it.name as name, it.state as item_state, " +
                "br.id as brand_id, br.name as brand_name, " +
                "br.state as brand_state, " +
                "ca.id as category_id, ca.name as category_name, " +
                "ca.state as category_state " +
                "FROM Shopping_Items as si " +
                "INNER JOIN Items it " +
                "ON it.id = si.item_id " +
                "INNER JOIN Brands br " +
                "ON br.id = it.brand_id " +
                "INNER JOIN Categories ca " +
                "ON ca.id = it.category_id " +
                "WHERE si.shopping_list_id = :shoppingListId"
    )
    @Transaction
    fun getShoppingItemsBy(shoppingListId: Long): List<CompositeShoppingItem>

    @Query("DELETE FROM Shopping_Items WHERE id = :id")
    fun delete(id: Long)

    @Query("UPDATE Shopping_Items SET state = 3 WHERE id = :id")
    fun inactivate(id: Long)
}