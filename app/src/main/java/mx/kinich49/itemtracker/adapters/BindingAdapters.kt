package mx.kinich49.itemtracker.adapters

import android.view.View
import android.widget.AdapterView
import android.widget.Button
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import mx.kinich49.itemtracker.DataInitializationState
import mx.kinich49.itemtracker.models.view.RecyclerItem
import mx.kinich49.itemtracker.models.view.Store
import mx.kinich49.itemtracker.views.AutoSuggestView

@BindingAdapter("items", "recycler_view_lifecycle_owner")
fun setRecyclerViewItems(
    recyclerView: RecyclerView,
    items: List<RecyclerItem>?,
    recyclerViewLifecycleOwner: LifecycleOwner
) {
    var adapter = recyclerView.adapter as? RecyclerViewAdapter
    if (adapter == null) {
        adapter = RecyclerViewAdapter(recyclerViewLifecycleOwner)
        recyclerView.adapter = adapter
    }
    adapter.updateData(items.orEmpty())
}

@BindingAdapter("dataState")
fun setEnabled(
    button: Button,
    initState: DataInitializationState?
) {
    button.isEnabled = initState?.canAddData ?: false
}

@InverseBindingAdapter(attribute = "app:item", event = "")
fun captureItem(view: AutoSuggestView): Any? {
    return view.item
}

@BindingAdapter("itemAttrChanged")
fun setItemListener(
    view: AutoSuggestView,
    itemChanged: InverseBindingListener
) {

    view.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(p0: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

        override fun onItemSelected(parent: AdapterView<*>?, _view: View?, pos: Int, id: Long) {
            (_view as? AutoSuggestView)?.item = parent?.selectedItem
            itemChanged.onChange()
        }

    }

    view.setOnItemClickListener { _, _, i, _ ->
        view.item = view.adapter.getItem(i)
        itemChanged.onChange()
    }
}