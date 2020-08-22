package mx.kinich49.itemtracker.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import mx.kinich49.itemtracker.Initialized
import mx.kinich49.itemtracker.NotInitialized
import mx.kinich49.itemtracker.R
import mx.kinich49.itemtracker.databinding.HomeLayoutBinding
import mx.kinich49.itemtracker.shoppingList.ItemTrackerViewModelFactory
import timber.log.Timber

class HomeFragment(itemTrackerViewModelFactory: ItemTrackerViewModelFactory) : Fragment() {

    private val viewModel: HomeViewModel by viewModels(factoryProducer = {
        itemTrackerViewModelFactory
    })

    private lateinit var binding: HomeLayoutBinding

    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.home_layout, container, false
        )

        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.addListEvent.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_homeFragment_to_shoppingListFragment)
        })

        viewModel.dataInitializationState.observe(viewLifecycleOwner, Observer { initState ->
            when (initState) {
                is NotInitialized -> {
                    snackbar = Snackbar.make(
                        binding.root,
                        initState.error,
                        Snackbar.LENGTH_INDEFINITE
                    )
                    snackbar?.show()
                }
                is Initialized -> {
                    Timber.tag("TEST").d("All good")
                    snackbar?.takeIf {
                        it.isShown
                    }?.dismiss()
                }
            }
        })
    }

}