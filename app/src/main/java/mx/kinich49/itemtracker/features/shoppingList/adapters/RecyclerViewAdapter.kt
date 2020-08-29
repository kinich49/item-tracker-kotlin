package mx.kinich49.itemtracker.features.shoppingList.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.blank_shopping_item_layout.view.*
import mx.kinich49.itemtracker.entities.database.ItemTrackerDatabase
import mx.kinich49.itemtracker.entities.database.extensions.toView
import mx.kinich49.itemtracker.features.shoppingList.models.Brand
import mx.kinich49.itemtracker.features.shoppingList.models.Category
import mx.kinich49.itemtracker.features.shoppingList.models.Item
import mx.kinich49.itemtracker.features.shoppingList.models.RecyclerItem

class RecyclerViewAdapter(private val lifecycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<RecyclerViewAdapter.BindingViewHolder>() {

    private val items = mutableListOf<RecyclerItem>()

    class BindingViewHolder(
        val binding: ViewDataBinding,
        lifecycleOwner: LifecycleOwner
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.executePendingBindings()
            binding.lifecycleOwner = lifecycleOwner
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            inflater, viewType,
            parent, false
        )

        val itemAdapter = AutoSuggestAdapter<Item>(parent.context)
        binding.root.item_field.setAdapter(itemAdapter)
        val itemDao = ItemTrackerDatabase.getDatabase(parent.context).itemDao()
        binding.root.item_field.addTextChangedListener { editable ->
            itemAdapter.clear()
            editable?.toString()?.let {
                itemDao.getItemsLike(it)
                    .map { i -> i.toView() }
                    .let { results ->
                        itemAdapter.addAll(results)
                    }
            }
        }

        val brandAdapter = AutoSuggestAdapter<Brand>(parent.context)
        binding.root.brand_field.setAdapter(brandAdapter)
        val brandDao = ItemTrackerDatabase.getDatabase(parent.context).brandDao()
        binding.root.brand_field.addTextChangedListener { editable ->
            brandAdapter.clear()
            editable?.toString()?.let {
                brandDao.getBrandsLike(it)
                    .map { b -> b.toView() }
                    .let { results ->
                        brandAdapter.addAll(results)
                    }
            }
        }

        val categoryAdapter = AutoSuggestAdapter<Category>(parent.context)
        binding.root.category_field.setAdapter(categoryAdapter)
        val categoryDao = ItemTrackerDatabase.getDatabase(parent.context).categoryDao()
        binding.root.category_field.addTextChangedListener { editable ->
            categoryAdapter.clear()
            editable?.toString()?.let {
                categoryDao.getCategoriesLike(it)
                    .map { c -> c.toView() }
                    .let { results ->
                        categoryAdapter.addAll(results)
                    }
            }
        }

        val units = mutableListOf(
            "Unit",
            "KG"
        )

        val unitAdapter = AutoSuggestAdapter(parent.context, units)
        binding.root.unit_field.setAdapter(unitAdapter)

        return BindingViewHolder(binding, lifecycleOwner)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).layoutId
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        getItem(position).bind(holder.binding)
        holder.binding.executePendingBindings()
    }

    private fun getItem(position: Int): RecyclerItem {
        return items[position]
    }

    fun updateData(newItems: List<RecyclerItem>) {
        this.items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

}