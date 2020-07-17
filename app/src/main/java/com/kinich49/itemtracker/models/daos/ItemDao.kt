package com.kinich49.itemtracker.models.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kinich49.itemtracker.models.Item
import com.kinich49.itemtracker.models.relations.ItemAndBrandAndCategory

@Dao
interface ItemDao {

    @Query("SELECT * FROM Items")
    fun getAllItems(): LiveData<List<Item>>

    @Query(
        "SELECT i.id as item_id, i.name as item_name, " +
                "i.brand_id as item_brand_id, i.category_id as item_category_id, " +
                "b.id as brand_id, b.name as brand_name," +
                "c.id as category_id, c.name as category_name " +
                "FROM Items as i " +
                "INNER JOIN Brands b " +
                "ON i.brand_id = b.id " +
                "INNER JOIN Categories c " +
                "ON i.category_id = c.id " +
                "WHERE i.id = :id"
    )
    fun getItemBrandAndCategory(id: Long): LiveData<ItemAndBrandAndCategory>

    @Insert
    fun insert(item: Item)
}