package mx.kinich49.itemtracker.entities.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Items",
    foreignKeys = [ForeignKey(
        entity = Brand::class,
        parentColumns = arrayOf("mobile_id"),
        childColumns = arrayOf("brand_id")
    ),
        ForeignKey(
            entity = Category::class,
            parentColumns = arrayOf("mobile_id"),
            childColumns = arrayOf("category_id")
        )]
)
data class Item(
    @PrimaryKey @ColumnInfo(name = "mobile_id")  val mobileId: Long? = null,
    @ColumnInfo(name = "remote_id") val remoteId: Long? = null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "brand_id") val brandId: Long?,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    @ColumnInfo(name = "state") var state: Long = 0
)