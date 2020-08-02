package mx.kinich49.itemtracker.models.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import mx.kinich49.itemtracker.models.database.ShoppingList
import mx.kinich49.itemtracker.models.database.relations.CompositeShoppingList

@Dao
interface ShoppingListDao {

    @Query("SELECT * FROM Shopping_Lists")
    fun getAllShoppingLists(): LiveData<ShoppingList>

    @Transaction
    @Query(
        "SELECT sl.id as shopping_list_id, sl.shopping_date, " +
                "sl.state as shopping_list_state, " +
                "st.id as store_id, st.name as store_name, st.state as store_state, " +
                "si.id as shopping_item_id, si.unit_price, si.currency, si.quantity, " +
                "si.state as shopping_item_state " +
                "FROM Shopping_Lists as sl " +
                "INNER JOIN Shopping_ITEMS si " +
                "ON si.shopping_list_id = sl.id " +
                "INNER JOIN Stores st " +
                "ON sl.store_id = st.id " +
                "WHERE sl.id = :id"
    )
    fun getShoppingListBy(id: Long): LiveData<CompositeShoppingList>

    @Insert
    fun insert(shoppingList: ShoppingList): Long

    @Insert
    fun insert(vararg shoppingList: ShoppingList)
}