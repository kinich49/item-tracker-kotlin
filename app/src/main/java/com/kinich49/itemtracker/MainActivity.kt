package com.kinich49.itemtracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kinich49.itemtracker.shoppigList.BlankShoppingListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        add_shopping_list.setOnClickListener {
            val intent = Intent(this, BlankShoppingListActivity::class.java)
            startActivity(intent)
        }
    }
}