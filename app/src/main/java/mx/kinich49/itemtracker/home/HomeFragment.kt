package mx.kinich49.itemtracker.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
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
}