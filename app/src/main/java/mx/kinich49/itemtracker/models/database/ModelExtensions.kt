package mx.kinich49.itemtracker.models.database

import mx.kinich49.itemtracker.models.database.relations.CompositeItem
import mx.kinich49.itemtracker.models.view.Item

fun Brand.toView(): mx.kinich49.itemtracker.models.view.Brand =
    mx.kinich49.itemtracker.models.view.Brand(this.id, this.name)

fun Category.toView(): mx.kinich49.itemtracker.models.view.Category =
    mx.kinich49.itemtracker.models.view.Category(this.id, this.name)

fun Store.toView(): mx.kinich49.itemtracker.models.view.Store =
    mx.kinich49.itemtracker.models.view.Store(this.id, this.name)

fun CompositeItem.toView(): Item {
    val brand = this.brand?.toView()
    val category = this.category.toView()
    return Item(this.item.id, brand, category, this.item.name)
}