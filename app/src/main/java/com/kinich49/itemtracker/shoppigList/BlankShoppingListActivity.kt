package com.kinich49.itemtracker.shoppigList

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kinich49.itemtracker.R
import com.kinich49.itemtracker.adapters.RecyclerViewAdapter
import com.kinich49.itemtracker.databinding.BlankShoppingItemLayoutBinding
import com.kinich49.itemtracker.databinding.BlankShoppingItemLayoutBindingImpl
import com.kinich49.itemtracker.databinding.BlankShoppingListLayoutBinding
import com.kinich49.itemtracker.databinding.BlankShoppingListLayoutBindingImpl
import com.kinich49.itemtracker.models.database.Brand
import com.kinich49.itemtracker.models.view.ShoppingItem
import kotlinx.android.synthetic.main.blank_shopping_list_layout.*

class BlankShoppingListActivity : AppCompatActivity() {

    private val viewModel: ShoppingListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: BlankShoppingListLayoutBinding =
            DataBindingUtil.setContentView(this, R.layout.blank_shopping_list_layout)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        fab.setOnClickListener {
            viewModel.addBlankShoppingItem()
        }

    }
}