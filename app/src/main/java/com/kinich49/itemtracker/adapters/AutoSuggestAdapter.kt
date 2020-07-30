package com.kinich49.itemtracker.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.kinich49.itemtracker.models.view.Store
import com.kinich49.itemtracker.views.AutoSuggestView

class AutoSuggestAdapter<Model>(
    context: Context,
    objects: MutableList<Model>
) : ArrayAdapter<Model>(
    context,
    android.R.layout.simple_dropdown_item_1line,
    objects
) {

    var item: Model? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        item = getItem(position)
        (convertView as? AutoSuggestView)?.item = item as Store?
        return view
    }
}