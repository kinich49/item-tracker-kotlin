package mx.kinich49.itemtracker.features.shoppingList.adapters

import android.content.Context
import android.widget.ArrayAdapter

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