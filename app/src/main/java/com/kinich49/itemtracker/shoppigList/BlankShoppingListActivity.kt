package com.kinich49.itemtracker.shoppigList

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kinich49.itemtracker.ItemTrackerDatabase
import com.kinich49.itemtracker.R
import com.kinich49.itemtracker.adapters.AutoSuggestAdapter
import com.kinich49.itemtracker.adapters.RecyclerViewAdapter
import com.kinich49.itemtracker.databinding.BlankShoppingItemLayoutBinding
import com.kinich49.itemtracker.databinding.BlankShoppingItemLayoutBindingImpl
import com.kinich49.itemtracker.databinding.BlankShoppingListLayoutBinding
import com.kinich49.itemtracker.databinding.BlankShoppingListLayoutBindingImpl
import com.kinich49.itemtracker.models.database.Brand
import com.kinich49.itemtracker.models.database.toView
import com.kinich49.itemtracker.models.view.ShoppingItem
import com.kinich49.itemtracker.models.view.Store
import kotlinx.android.synthetic.main.blank_shopping_list_layout.*
import timber.log.Timber
import java.time.LocalDate
import java.util.*

class BlankShoppingListActivity : AppCompatActivity() {

    private val viewModel: ShoppingListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: BlankShoppingListLayoutBinding =
            DataBindingUtil.setContentView(this, R.layout.blank_shopping_list_layout)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val storeAdapter: AutoSuggestAdapter<Store> = AutoSuggestAdapter(baseContext)
        val storeDao = ItemTrackerDatabase.getDatabase(this).storeDao()

        binding.storeInputField.addTextChangedListener { editable ->
            storeAdapter.clear()
            editable?.toString()?.let {
                storeDao.getStoresLike(it)
                    .map { s -> s.toView() }
                    .let { results ->
                        storeAdapter.addAll(results)
                    }
            }
        }


        viewModel.datePickerEvent.observe(this, Observer {
            val year = it.year
            //Calendar uses 0-11, LocalDate uses 1-12
            val month = it.month.minus(1).value
            val day = it.dayOfMonth

            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, _year, monthOfYear, dayOfMonth ->
                    //Increase monthOfYear by one
                    //Calendar/LocalDate compatibility
                    val monthOfYearCompat = monthOfYear + 1
                    val selectedShoppingDate = LocalDate.of(_year, monthOfYearCompat, dayOfMonth)
                    viewModel.onShoppingDateSelected(selectedShoppingDate)
                },
                year,
                month,
                day
            )
            dpd.show()
        })

        store_input_field.setAdapter(storeAdapter)

        fab.setOnClickListener {
            viewModel.addBlankShoppingItem()
        }

    }
}