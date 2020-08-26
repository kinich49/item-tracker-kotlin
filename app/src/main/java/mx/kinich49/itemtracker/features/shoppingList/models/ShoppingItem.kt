package mx.kinich49.itemtracker.features.shoppingList.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import mx.kinich49.itemtracker.BR
import mx.kinich49.itemtracker.R
import java.text.DecimalFormat

class ShoppingItem(
    var id: Long? = null
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
            //notifyPropertyChanged(BR.quantity)
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
            this.name = value?.name
            notifyPropertyChanged(BR.item)
            notifyPropertyChanged(BR.name)
        }

    @get: Bindable
    var name: String? = null
    set(value) {
        field = value
        notifyPropertyChanged(BR.name)
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