package com.kinich49.itemtracker.models.view

import androidx.databinding.ObservableField

data class Store(
    var id: Long? = null,
    var name: String? = null
) {

    override fun toString(): String = name ?: "Store id: $id"

    companion object {
        fun from(store: com.kinich49.itemtracker.models.database.Store): Store =
            Store(store.id, store.name)
    }
}