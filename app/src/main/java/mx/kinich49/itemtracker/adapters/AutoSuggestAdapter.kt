package mx.kinich49.itemtracker.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import mx.kinich49.itemtracker.models.view.Store
import mx.kinich49.itemtracker.views.AutoSuggestView

class AutoSuggestAdapter<Model> : ArrayAdapter<Model> {
    constructor(context: Context) : super(
        context,
        android.R.layout.simple_dropdown_item_1line
    )

    constructor(context: Context, objects: MutableList<Model>) : super(
        context,
        android.R.layout.simple_dropdown_item_1line,
        objects
    )

    var item: Model? = null

}