package com.kinich49.itemtracker.models.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kinich49.itemtracker.models.database.Brand
import com.kinich49.itemtracker.models.database.Item
import com.kinich49.itemtracker.models.database.relations.CompositeItem

@Dao
interface ItemDao {

    @Query(
        "SELECT it.name as item_name, it.id as item_id, " +
                "it.brand_id as item_brand_id, it.category_id as item_category_id, " +
                "br.id as brand_id, br.name as brand_name, " +
                "ca.id as category_id, ca.name as category_name " +
                "FROM Items as it " +
                "INNER JOIN Brands br " +
                "ON br.id = it.brand_id " +
                "INNER JOIN Categories ca " +
                "ON ca.id = it.category_id"
    )
    fun getAllItems(): LiveData<List<CompositeItem>>

    @Query(
        "SELECT it.name as item_name, it.id as item_id, " +
                "it.brand_id as item_brand_id, it.category_id as item_category_id, " +
                "br.id as brand_id, br.name as brand_name, " +
                "ca.id as category_id, ca.name as category_name " +
                "FROM Items as it " +
                "INNER JOIN Brands br " +
                "ON br.id = it.brand_id " +
                "INNER JOIN Categories ca " +
                "ON ca.id = it.category_id " +
                "WHERE it.name LIKE  '%' || :name || '%'"
    )
    fun getItemsLike(name: String): List<CompositeItem>

//    @Query(
//        "SELECT i.id as item_id, i.name as item_name, " +
//                "i.brand_id as item_brand_id, i.category_id as item_category_id, " +
//                "b.id as brand_id, b.name as brand_name," +
//                "c.id as category_id, c.name as category_name " +
//                "FROM Items as i " +
//                "INNER JOIN Brands b " +
//                "ON i.brand_id = b.id " +
//                "INNER JOIN Categories c " +
//                "ON i.category_id = c.id " +
//                "WHERE i.id = :id"
//    )
//    fun getItemBrandAndCategory(id: Long): LiveData<CompositeItem>

    @Insert
    fun insert(item: Item)

    @Insert
    fun insert(vararg item: Item)
}