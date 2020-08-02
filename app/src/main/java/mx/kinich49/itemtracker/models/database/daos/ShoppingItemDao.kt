package mx.kinich49.itemtracker.models.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import mx.kinich49.itemtracker.models.database.ShoppingItem

@Dao
interface ShoppingItemDao {

    @Query("SELECT * FROM Shopping_Items")
    fun getAllShoppingItems(): List<ShoppingItem>

    @Insert
    fun insert(shoppingItem: ShoppingItem)

    @Insert
    fun insert(vararg shoppingItem: ShoppingItem)

//    @Query(
//        "SELECT si.id, si.unit_price, si.currency, si.quantity, " +
//                "si.shopping_list_id, si.state as shopping_item_sate, " +
//                "it.id as item_id, it.name as item_name, " +
//                "it.brand_id as item_brand_id, it.category_id as item_category_id, " +
//                "it.state as item_state, " +
//                "br.id as brand_id, br.name as brand_name, br.state as brand_state, " +
//                "ca.id as category_id, ca.name as category_name, ca.state as category_sate " +
//                "FROM Shopping_Items AS si " +
//                "INNER JOIN Items it " +
//                "ON it.id = si.item_id " +
//                "INNER JOIN Brands br " +
//                "ON br.id = it.brand_id " +
//                "INNER JOIN Categories ca " +
//                "ON ca.id = it.category_id " +
//                "WHERE si.id = :id"
//    )
//    fun getShoppingItem(id: Long): LiveData<CompositeShoppingItem>

//    @Query(
//        "SELECT si.id, si.unit_price, si.currency, " +
//                "si.quantity, si.shopping_list_id, " +
//                "it.id as item_id, it.name as item_name, " +
//                "it.brand_id as item_brand_id, " +
//                "it.category_id as item_category_id, " +
//                "br.id as brand_id, br.name as brand_name," +
//                "ca.id as category_id, ca.name as category_name " +
//                "FROM Shopping_Items AS si " +
//                "INNER JOIN Items it " +
//                "ON it.id = si.item_id " +
//                "INNER JOIN Brands br " +
//                "ON br.id = it.brand_id " +
//                "INNER JOIN Categories ca " +
//                "ON ca.id = it.category_id " +
//                "WHERE si.shopping_list_id = :shoppingListId"
//    )
//    fun getShoppingItemsBy(shoppingListId: Long): LiveData<List<CompositeShoppingItem>>
}