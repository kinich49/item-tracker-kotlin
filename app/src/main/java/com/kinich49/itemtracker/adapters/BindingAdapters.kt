package com.kinich49.itemtracker.adapters

import android.view.View
import android.widget.AdapterView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.RecyclerView
import com.kinich49.itemtracker.models.view.RecyclerItem
import com.kinich49.itemtracker.models.view.Store
import com.kinich49.itemtracker.views.AutoSuggestView

@BindingAdapter("items")
fun setRecyclerViewItems(
    recyclerView: RecyclerView,
    items: List<RecyclerItem>?
) {
    var adapter = recyclerView.adapter as? RecyclerViewAdapter
    if (adapter == null) {
        adapter = RecyclerViewAdapter()
        recyclerView.adapter = adapter
    }
    adapter.updateData(items.orEmpty())
}

@InverseBindingAdapter(attribute = "app:item", event = "")
fun captureStore(view: AutoSuggestView): Store? {
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
            (_view as? AutoSuggestView)?.item = parent?.selectedItem as Store?
            itemChanged.onChange()
        }

    }

    view.setOnItemClickListener { _, _, i, _ ->
        view.item = view.adapter.getItem(i) as? Store?
        itemChanged.onChange()
    }
}