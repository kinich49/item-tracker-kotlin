package mx.kinich49.itemtracker.models.view

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

data class RecyclerItem (
    val data: Any,
    @LayoutRes val layoutId: Int,
    val variableId: Int
){
    fun bind(binding: ViewDataBinding){
        binding.setVariable(variableId, data)
    }
}