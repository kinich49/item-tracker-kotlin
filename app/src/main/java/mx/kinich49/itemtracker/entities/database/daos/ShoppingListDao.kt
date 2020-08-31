package mx.kinich49.itemtracker.entities.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import mx.kinich49.itemtracker.entities.database.models.ShoppingList
import mx.kinich49.itemtracker.entities.database.models.relationships.CompositeShoppingList

@Dao
interface ShoppingListDao {

    @Query("SELECT * FROM Shopping_Lists")
    fun getAllShoppingLists(): ShoppingList

    @Transaction
    @Query(
        "SELECT sl.mobile_id as shopping_list_mobile_id, " +
                "sl.remote_id as shopping_list_remote_id, " +
                "sl.shopping_date, " +
                "sl.state as shopping_list_state, " +
                "st.mobile_id as store_mobile_id, st.remote_id as store_remote_id, " +
                "st.name as store_name, st.state as store_state " +
                "FROM Shopping_Lists as sl " +
                "INNER JOIN Stores st " +
                "ON st.mobile_id = sl.store_id " +
                "WHERE sl.state = 1"
    )
    fun getPendingShoppingLists(): List<CompositeShoppingList>

    @Insert
    fun insert(shoppingList: ShoppingList): Long

    @Insert
    fun insert(vararg shoppingList: ShoppingList)

    @Query("DELETE FROM Shopping_Lists WHERE mobile_id = :id")
    fun delete(id: Long)

    @Query("UPDATE Shopping_Lists SET state = 3 WHERE mobile_id = :id")
    fun inactivate(id: Long)
}