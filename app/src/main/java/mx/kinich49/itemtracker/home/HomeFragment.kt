package mx.kinich49.itemtracker.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mx.kinich49.itemtracker.R
import mx.kinich49.itemtracker.databinding.HomeLayoutBinding
import mx.kinich49.itemtracker.shoppingList.ItemTrackerViewModelFactory

class HomeFragment(itemTrackerViewModelFactory: ItemTrackerViewModelFactory) : Fragment() {

    private val viewModel: HomeViewModel by viewModels(factoryProducer = {
        itemTrackerViewModelFactory
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: HomeLayoutBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.home_layout, container, false
        )
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.addListEvent.observe(this, Observer {
            findNavController().navigate(R.id.action_homeFragment_to_shoppingListFragment)
        })
    }
}