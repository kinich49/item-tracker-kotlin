package com.kinich49.itemtracker.models.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kinich49.itemtracker.models.ShoppingItem
import com.kinich49.itemtracker.models.relations.CompositeShoppingItem

@Dao
interface ShoppingItemDao {

    @Query("SELECT * FROM Shopping_Items")
    fun getAllShoppingItems(): LiveData<List<ShoppingItem>>

    @Insert
    fun insert(shoppingItem: ShoppingItem)

    @Insert
    fun insert(vararg shoppingItem: ShoppingItem)

    @Query(
        "SELECT si.id, si.unit_price, si.currency, si.quantity, si.shopping_list_id," +
                "it.id as item_id, it.name as item_name, " +
                "it.brand_id as item_brand_id, it.category_id as item_category_id, " +
                "br.id as brand_id, br.name as brand_name," +
                "ca.id as category_id, ca.name as category_name " +
                "FROM Shopping_Items AS si " +
                "INNER JOIN Items it " +
                "ON it.id = si.item_id " +
                "INNER JOIN Brands br " +
                "ON br.id = it.brand_id " +
                "INNER JOIN Categories ca " +
                "ON ca.id = it.category_id " +
                "WHERE si.id = :id"
    )
    fun getShoppingItem(id: Long): LiveData<CompositeShoppingItem>

    @Query(
        "SELECT si.id, si.unit_price, si.currency, " +
                "si.quantity, si.shopping_list_id, " +
                "it.id as item_id, it.name as item_name, " +
                "it.brand_id as item_brand_id, " +
                "it.category_id as item_category_id, " +
                "br.id as brand_id, br.name as brand_name," +
                "ca.id as category_id, ca.name as category_name " +
                "FROM Shopping_Items AS si " +
                "INNER JOIN Items it " +
                "ON it.id = si.item_id " +
                "INNER JOIN Brands br " +
                "ON br.id = it.brand_id " +
                "INNER JOIN Categories ca " +
                "ON ca.id = it.category_id " +
                "WHERE si.shopping_list_id = :shoppingListId"
    )
    fun getShoppingItemsBy(shoppingListId: Long): LiveData<List<CompositeShoppingItem>>
}