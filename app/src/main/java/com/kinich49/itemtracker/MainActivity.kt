package com.kinich49.itemtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.kinich49.itemtracker.models.*
import timber.log.Timber
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("On Create")
//        val db = ItemTrackerDatabase.getDatabase(this)
//
//        val brandDao = db.brandDao()
//        val categoryDao = db.categoryDao()
//        val itemDao = db.itemDao()
//        val storeDao = db.storeDao()
//        val shoppingListDao = db.shoppingListDao()
//        val shoppingItemDao = db.shoppingItemDao()
//
//        val brand_A = Brand(1, "Test Brand A")
//        val brand_B = Brand(2, "Test Brand B")
//        val brand_C = Brand(3, "Test Brand C")
//
//        val category_A = Category(1, "Test Category A")
//        val category_B = Category(2, "Test Category B")
//        val category_C = Category(3, "Test Category C")
//
//        val item_A = Item(1, "Test Item A", 1, 1)
//        val item_B = Item(2, "Test Item A", 2, 2)
//        val item_C = Item(3, "Test Item A", 3, 3)
//
//        val store_A = Store(1L, "Store A")
//
//        brandDao.insert(brand_A)
//        brandDao.insert(brand_B)
//        brandDao.insert(brand_C)
//
//        categoryDao.insert(category_A)
//        categoryDao.insert(category_B)
//        categoryDao.insert(category_C)
//
//        itemDao.insert(item_A)
//        itemDao.insert(item_B)
//        itemDao.insert(item_C)
//
//        storeDao.insert(store_A)
//
//        val shoppingList_A = ShoppingList(1L, LocalDate.now(), 1L)
//        val shoppingList_B = ShoppingList(2L, LocalDate.now(), 1L)
//
//        shoppingListDao.insert(shoppingList_A)
//
//        val shoppingItem_AA = ShoppingItem(
//            1L, "MXN", 1.0, 1500,
//            1L, 1L
//        )
//
//        val shoppingItem_AB = ShoppingItem(
//            2L, "MXN", 1.0, 3500,
//            1L, 2L
//        )
//        shoppingItemDao.insert(shoppingItem_AA)
//        shoppingItemDao.insert(shoppingItem_AB)
//
//        itemDao.getItemBrandAndCategory(1L).observe(this, Observer { results ->
//            results?.let { Timber.d(results.toString()) }
//        })
//
//        shoppingListDao.getShoppingListBy(1L).observe(this, Observer { results ->
//            results?.let { Timber.d(results.toString()) }
//        })
//
//
//        shoppingListDao.getAllShoppingLists().observe(this, Observer { results ->
//            results?.let { Timber.d(results.toString()) }
//        })
//
//        shoppingItemDao.getShoppingItemsBy(1L).observe(this, Observer { results ->
//            results?.let {
//                Timber.tag("ITEM_TRACKER")
//                Timber.d(results.toString()) }
//        })
//
//
//        shoppingItemDao.getAllShoppingItems().observe(this, Observer {
//                results -> results?.let { Timber.d(results.toString()) }
//        })
    }
}