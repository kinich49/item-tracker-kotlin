package com.kinich49.itemtracker.shoppigList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kinich49.itemtracker.R
import com.kinich49.itemtracker.models.view.ShoppingItem
import timber.log.Timber

class ShoppingListAdapter(private val shoppingItems: MutableList<ShoppingItem>?) :
    RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(shoppingItem: ShoppingItem) = with(itemView) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.blank_shopping_item_layout, parent, false)

        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return shoppingItems?.size ?: 0;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        shoppingItems?.get(position)?.let {
            holder.bind(it)
        }
    }

    fun addShoppingItem(shoppingItem: ShoppingItem) = shoppingItems?.let {
        Timber.tag("TEST").d("Add Shopping Item")
        it.add(shoppingItem)
        notifyDataSetChanged()
    }
}