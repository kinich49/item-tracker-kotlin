package com.kinich49.itemtracker.models.database

import com.kinich49.itemtracker.models.database.relations.CompositeItem
import com.kinich49.itemtracker.models.view.Item

fun Brand.toView(): com.kinich49.itemtracker.models.view.Brand =
    com.kinich49.itemtracker.models.view.Brand(this.id, this.name)

fun Category.toView(): com.kinich49.itemtracker.models.view.Category =
    com.kinich49.itemtracker.models.view.Category(this.id, this.name)

fun Store.toView(): com.kinich49.itemtracker.models.view.Store =
    com.kinich49.itemtracker.models.view.Store(this.id, this.name)

fun CompositeItem.toView(): Item {
    val brand = this.brand?.toView()
    val category = this.category.toView()
    return Item(this.item.id, brand, category, this.item.name)
}