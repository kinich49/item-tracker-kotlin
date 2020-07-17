package com.kinich49.itemtracker.models.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.kinich49.itemtracker.models.ShoppingList
import com.kinich49.itemtracker.models.relations.CompositeShoppingList

@Dao
interface ShoppingListDao {

    @Query("SELECT * FROM Shopping_Lists")
    fun getAllShoppingLists(): LiveData<ShoppingList>

    @Transaction
    @Query(
        "SELECT sl.id as shopping_list_id, sl.shopping_date, " +
                "st.id as store_id, st.name as store_name, " +
                "si.id as shopping_item_id, si.unit_price, si.currency, si.quantity " +
                "FROM Shopping_Lists as sl " +
                "INNER JOIN Shopping_ITEMS si " +
                "ON si.shopping_list_id = sl.id " +
                "INNER JOIN Stores st " +
                "ON sl.store_id = st.id " +
                "WHERE sl.id = :id"
    )
    fun getShoppingListBy(id: Long): LiveData<CompositeShoppingList>

    @Insert
    fun insert(shoppingList: ShoppingList)
}