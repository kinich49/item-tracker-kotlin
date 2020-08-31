package mx.kinich49.itemtracker.features.shoppingList.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import mx.kinich49.itemtracker.BR
import mx.kinich49.itemtracker.R
import timber.log.Timber
import java.text.DecimalFormat

class ShoppingItem : BaseObservable() {

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
        Timber.tag("TEST").d("brandMediatorObserver")
    }

    private var itemMediatorObserver = Observer<Item> {
        Timber.tag("TEST").d("itemMediatorObserver")
    }

    init {
        categoryMediator.addSource(category) { value ->
            categoryMediator.value = value
        }

        categoryMediator.addSource(categoryName) { value ->
            val mediatorData =
                if (categoryMediator.value == null) Category() else categoryMediator.value

            if (!mediatorData?.name.equals(value)) {
                mediatorData?.name = value
                mediatorData?.id = null
                categoryMediator.value = mediatorData
            }
        }

        categoryMediator.observeForever(categoryMediatorObserver)

        brandMediator.addSource(brand) { value ->
            brandMediator.value = value
        }

        brandMediator.addSource(brandName) { value ->
            val mediatorData =
                if (brandMediator.value == null) Brand() else brandMediator.value

            if (!mediatorData?.name.equals(value)) {
                mediatorData?.name = value
                mediatorData?.id = null
                brandMediator.value = mediatorData
            }
        }

        brandMediator.observeForever(brandMediatorObserver)

        itemMediator.addSource(itemName) { value ->
            val mediatorData =
                if (itemMediator.value == null) Item() else itemMediator.value
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