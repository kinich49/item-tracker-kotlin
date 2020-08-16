package mx.kinich49.itemtracker.shoppigList

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import mx.kinich49.itemtracker.ItemTrackerDatabase
import mx.kinich49.itemtracker.R
import mx.kinich49.itemtracker.adapters.AutoSuggestAdapter
import mx.kinich49.itemtracker.databinding.BlankShoppingListLayoutBinding
import mx.kinich49.itemtracker.models.database.toView
import mx.kinich49.itemtracker.models.view.Store
import mx.kinich49.itemtracker.shoppigList.impl.SaveShoppingJobImpl
import kotlinx.android.synthetic.main.blank_shopping_list_layout.*
import mx.kinich49.itemtracker.remote.SchedulerProvider
import java.time.LocalDate

class BlankShoppingListActivity : AppCompatActivity() {

    private val viewModel: ShoppingListViewModel by viewModels {
        val db = ItemTrackerDatabase.getDatabase(this)
        val saveShoppingJob: SaveShoppingJob = SaveShoppingJobImpl(
            db.shoppingListDao(), db.storeDao(), db.shoppingItemDao(),
            db.brandDao(), db.categoryDao(), db.itemDao()
        )
        ShoppingListFactory(saveShoppingJob, SchedulerProvider.DEFAULT)
    }

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

        viewModel.onShoppingComplete.observe(this, Observer {
            setResult(Activity.RESULT_OK)
            finish()
        })
    }
}