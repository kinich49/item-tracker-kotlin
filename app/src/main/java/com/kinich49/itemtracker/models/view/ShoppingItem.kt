package com.kinich49.itemtracker.models.view

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.kinich49.itemtracker.BR
import com.kinich49.itemtracker.R
import java.text.DecimalFormat

class ShoppingItem(
    val id: Long,
    var name: String? = null
) : BaseObservable() {

    @get: Bindable
    var unit: String = "Unit"
        set(value) {
            field = value
            notifyPropertyChanged(BR.unit)
        }
    val currency: String = "MXN"

    @get: Bindable
    var unitPrice: String = ""
        set(value) {
            field = value
            updateTotalPrice()
            notifyPropertyChanged(BR.unitPrice)
        }

    @get: Bindable
    var quantity: String = ""
        set(value) {
            field = value
            updateTotalPrice()
            notifyPropertyChanged(BR.quantity)
        }

    @get: Bindable
    var totalPrice: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalPrice)
        }

    @get: Bindable
    var category: Category? = Category()
        set(value) {
            field = value
            notifyPropertyChanged(BR.category)
        }

    @get: Bindable
    var brand: Brand? = Brand()
        set(value) {
            field = value
            notifyPropertyChanged(BR.brand)
        }

    @get: Bindable
    var item: Item? = null
        set(value) {
            field = value
            this.brand = value?.brand
            this.category = value?.category
        }

    private fun updateTotalPrice() {
        if (unitPrice.isNotEmpty() && quantity.isNotEmpty()) {
            val result = unitPrice.toDouble() * quantity.toDouble()
            totalPrice = "$${priceFormatter.format(result)} $currency"
        }
    }

    fun toRecyclerItem() =
        RecyclerItem(
            data = this,
            layoutId = R.layout.blank_shopping_item_layout,
            variableId = BR.shoppingItem
        )

    companion object {
        private val priceFormatter: DecimalFormat = DecimalFormat("#,###.##")
    }
}