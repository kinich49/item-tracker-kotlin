package mx.kinich49.itemtracker.features.shoppingList.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import mx.kinich49.itemtracker.BR
import mx.kinich49.itemtracker.R
import java.text.DecimalFormat

class ShoppingItemViewModel : BaseObservable() {

    val category = MutableLiveData<Category>(Category())
    val categoryName = MutableLiveData<String>()
    val categoryMediator = MediatorLiveData<Category>()

    val brand = MutableLiveData<Brand>(Brand())
    val brandName = MutableLiveData<String>()
    val brandMediator = MediatorLiveData<Brand>()

    val item = MutableLiveData<Item>(Item())
    val itemName = MutableLiveData<String>()
    val itemMediator = MediatorLiveData<Item>()

    private var categoryMediatorObserver = Observer<Category> {
        //Empty observer to activate categoryMediator
    }

    private var brandMediatorObserver = Observer<Brand> {
        //Empty observer to activate brandMediator
    }

    private var itemMediatorObserver = Observer<Item> {
        //Empty observer to activate itemMediator
    }

    init {
        categoryMediator.addSource(category) { value ->
            categoryMediator.value = value
        }

        categoryMediator.addSource(categoryName) { value ->
            val mediatorData = categoryMediator.value
            mediatorData?.name = value
            mediatorData?.id = null
            categoryMediator.value = mediatorData
        }

        categoryMediator.observeForever(categoryMediatorObserver)

        brandMediator.addSource(brand) { value ->
            brandMediator.value = value
        }

        brandMediator.addSource(brandName) { value ->
            val mediatorData = brandMediator.value
            mediatorData?.name = value
            mediatorData?.id = null
            brandMediator.value = mediatorData
        }

        brandMediator.observeForever(brandMediatorObserver)

        itemMediator.addSource(itemName) { value ->
            val mediatorData = itemMediator.value
            mediatorData?.name = value
            mediatorData?.id = null
            itemMediator.value = mediatorData
        }

        itemMediator.addSource(item) { value ->
            itemMediator.value = value
            brandName.value = value.brand?.name
            brand.value = value.brand
            categoryName.value = value.category?.name
            category.value = value.category
        }

        itemMediator.observeForever(itemMediatorObserver)
    }

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
    var unit: String = "Unit"
        set(value) {
            field = value
            notifyPropertyChanged(BR.unit)
        }

    val currency: String = "MXN"

    @get: Bindable
    var totalPrice: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalPrice)
        }


    private fun updateTotalPrice() {
        if (unitPrice.isNotEmpty() && quantity.isNotEmpty()) {
            val result = unitPrice.toDouble() * quantity.toDouble()
            totalPrice = "$${priceFormatter.format(result)} $currency"
        }
    }

    fun onRemoveObservers() {
        categoryMediator.removeObserver(categoryMediatorObserver)
        brandMediator.removeObserver(brandMediatorObserver)
        itemMediator.removeObserver(itemMediatorObserver)
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