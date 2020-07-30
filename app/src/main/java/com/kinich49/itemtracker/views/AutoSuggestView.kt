package com.kinich49.itemtracker.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.kinich49.itemtracker.models.view.Store

class AutoSuggestView : AppCompatAutoCompleteTextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var item: Store? = null
}