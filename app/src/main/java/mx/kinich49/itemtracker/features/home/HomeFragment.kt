package mx.kinich49.itemtracker.features.home

import android.app.AlertDialog
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
import mx.kinich49.itemtracker.R
import mx.kinich49.itemtracker.databinding.HomeLayoutBinding
import mx.kinich49.itemtracker.entities.states.DataInitializationState
import mx.kinich49.itemtracker.entities.states.InProgress
import mx.kinich49.itemtracker.entities.states.Success
import mx.kinich49.itemtracker.features.factories.ItemTrackerViewModelFactory

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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAddListEvent()
        observeInitialData()
        observeUpstreamSyncProcess()
    }

    private fun observeAddListEvent() {
        viewModel.addListEvent.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_homeFragment_to_shoppingListFragment)
        })
    }

    private fun observeInitialData() {
        viewModel.dataInitState.observe(viewLifecycleOwner, Observer { initState ->
            when (initState) {
                is InProgress.Enqueued -> {
                    snackbar = Snackbar.make(
                        binding.root,
                        initState.message,
                        Snackbar.LENGTH_INDEFINITE
                    )
                    snackbar?.setAction(R.string.cancel) {
                        viewModel.onCancelEnqueuedInitialization()
                    }
                    snackbar?.show()
                }

                is Success.DataDownloaded, Success.NoData -> {
                    snackbar?.dismiss()
                }

                is InProgress.Downloading -> {
                    snackbar?.dismiss()
                    snackbar =
                        Snackbar.make(binding.root, initState.message, Snackbar.LENGTH_INDEFINITE)

                    snackbar?.show()
                }
                is DataInitializationState.Error -> {
                    snackbar?.dismiss()
                    AlertDialog.Builder(context)
                        .setTitle(R.string.something_went_wrong)
                        .setMessage(R.string.retry_or_cancel_initial_data)
                        .setNeutralButton(R.string.cancel) { dialog, _ ->
                            viewModel.onCancelFailedInitialization()
                            dialog.dismiss()
                        }
                        .setPositiveButton(R.string.retry) { dialog, _ ->
                            viewModel.onRetryFailedInitialization()
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        })
    }

    private fun observeUpstreamSyncProcess() {
        viewModel.upstreamSyncState.observe(viewLifecycleOwner, Observer { initState ->
            when (initState) {
                is InProgress.Enqueued -> {
                    snackbar = Snackbar.make(
                        binding.root,
                        initState.message,
                        Snackbar.LENGTH_INDEFINITE
                    )
                    snackbar?.show()
                }

                is Success.DataDownloaded, Success.NoData -> {
                    snackbar?.dismiss()
                }

                is InProgress.Downloading -> {
                    snackbar?.dismiss()
                    snackbar =
                        Snackbar.make(binding.root, initState.message, Snackbar.LENGTH_INDEFINITE)

                    snackbar?.show()
                }
                is DataInitializationState.Error -> {
                    snackbar?.dismiss()
                    AlertDialog.Builder(context)
                        .setTitle(R.string.something_went_wrong)
                        .setMessage(R.string.retry_upstream_sync)
                        .setPositiveButton(R.string.retry) { dialog, _ ->
                            viewModel.onRetryFailedUpstreamSync()
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        })
    }

}