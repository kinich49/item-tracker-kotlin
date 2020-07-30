package com.kinich49.itemtracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kinich49.itemtracker.models.view.Brand
import com.kinich49.itemtracker.models.view.Category
import com.kinich49.itemtracker.models.view.Item
import com.kinich49.itemtracker.models.view.RecyclerItem
import kotlinx.android.synthetic.main.blank_shopping_item_layout.view.*

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.BindingViewHolder>() {

    private val items = mutableListOf<RecyclerItem>()

    class BindingViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            inflater, viewType,
            parent, false
        )

        val items = mutableListOf(
            Item(1L, name = "Test item 1"),
            Item(2L, name = "Test item 2")
        )
        val itemAdapter = AutoSuggestAdapter(parent.context, items)
        binding.root.item_field.setAdapter(itemAdapter)

        val brands = mutableListOf(
            Brand(1L, "Test Brand 1"),
            Brand(2L, "Test Brand 2"),
            Brand(3L, "Test Brand 3")
        )
        val brandAdapter = AutoSuggestAdapter(parent.context, brands)
        binding.root.brand_field.setAdapter(brandAdapter)

        val categories = mutableListOf(
            Category(1L, "Test Category 1"),
            Category(2L, "Test Category 2"),
            Category(3L, "Test Category 3")
        )

        val categoryAdapter = AutoSuggestAdapter(parent.context, categories)
        binding.root.category_field.setAdapter(categoryAdapter)

        val units = mutableListOf(
            "Unit",
            "KG"
        )
        val unitAdapter = AutoSuggestAdapter(parent.context, units)
        binding.root.unit_field.setAdapter(unitAdapter)

        return BindingViewHolder(binding)
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