package mx.kinich49.itemtracker.shoppingList

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import mx.kinich49.itemtracker.ItemTrackerDatabase
import mx.kinich49.itemtracker.R
import mx.kinich49.itemtracker.adapters.AutoSuggestAdapter
import mx.kinich49.itemtracker.databinding.BlankShoppingListLayoutBinding
import mx.kinich49.itemtracker.models.database.toView
import mx.kinich49.itemtracker.models.view.Store
import java.time.LocalDate

class ShoppingListFragment(itemTrackerViewModelFactory: ItemTrackerViewModelFactory) :
    Fragment() {

    private val viewModel: ShoppingListViewModel by viewModels(factoryProducer = {
        itemTrackerViewModelFactory
    })

    private lateinit var binding: BlankShoppingListLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.blank_shopping_list_layout, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val context = requireContext()
        val storeAdapter: AutoSuggestAdapter<Store> = AutoSuggestAdapter(context)
        val storeDao = ItemTrackerDatabase.getDatabase(context).storeDao()

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
                context,
                { _, _year, monthOfYear, dayOfMonth ->
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

        binding.storeInputField.setAdapter(storeAdapter)

        viewModel.onShoppingComplete.observe(this, Observer {
            findNavController()
                .popBackStack(R.id.homeFragment, false)
        })
        
        return binding.root
    }
}