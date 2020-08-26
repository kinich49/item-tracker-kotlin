package mx.kinich49.itemtracker.entities.database

import mx.kinich49.itemtracker.entities.database.models.Brand
import mx.kinich49.itemtracker.entities.database.models.Category
import mx.kinich49.itemtracker.entities.database.models.Store
import mx.kinich49.itemtracker.entities.database.extensions.toRequest
import mx.kinich49.itemtracker.entities.database.models.relationships.CompositeShoppingItem
import mx.kinich49.itemtracker.entities.database.models.relationships.CompositeShoppingList
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate

class KotlinExtensionsTest {

    @Test
    fun transform_toRequest_existingStore() {
        //given
        val storeId = 1L
        val storeName = "Android Test Store"
        val store = Store(storeId, storeName, 0)

        //when
        val request = store.toRequest()

        //then
        assertNotNull(store)
        assertEquals(storeId, request.id)
        assertNull(request.mobileId)
        assertEquals(storeName, request.name)
    }

    @Test
    fun transform_toRequest_newStore() {
        //given
        val storeId = 1L
        val storeName = "Android Test Store"
        val store = Store(storeId, storeName, 1)

        //when
        val request = store.toRequest()

        //then
        assertNotNull(store)
        assertNull(request.id)
        assertEquals(storeId, request.mobileId)
        assertEquals(storeName, request.name)
    }

    @Test
    fun transform_toRequest_existingBrand() {
        //given
        val brandId = 1L
        val brandName = "Android Test Brand"
        val brand = Brand(brandId, brandName, 0)

        //when
        val request = brand.toRequest()

        //then
        assertNotNull(brand)
        assertEquals(brandId, request.id)
        assertNull(request.mobileId)
        assertEquals(brandName, request.name)
    }

    @Test
    fun transform_toRequest_newBrand() {
        //given
        val brandId = 1L
        val brandName = "Android Test Brand"
        val brand = Brand(brandId, brandName, 1)

        //when
        val request = brand.toRequest()

        //then
        assertNotNull(brand)
        assertNull(request.id)
        assertEquals(brandId, request.mobileId)
        assertEquals(brandName, request.name)
    }

    @Test
    fun transform_toRequest_existingCategory() {
        //given
        val categoryId = 1L
        val categoryName = "Android Test Brand"
        val category = Category(categoryId, categoryName, 0)

        //when
        val request = category.toRequest()

        //then
        assertNotNull(category)
        assertEquals(categoryId, request.id)
        assertNull(request.mobileId)
        assertEquals(categoryName, request.name)
    }

    @Test
    fun transform_toRequest_newCategory() {
        //given
        val categoryId = 1L
        val categoryName = "Android Test Brand"
        val category = Category(categoryId, categoryName, 1)

        //when
        val request = category.toRequest()

        //then
        assertNotNull(category)
        assertNull(request.id)
        assertEquals(categoryId, request.mobileId)
        assertEquals(categoryName, request.name)
    }

    @Test
    fun transform_toRequest_newItem() {
        //given
        val shoppingItemId = 10L
        val itemId = 20L
        val itemName = "Android Test Item"
        val unit = "Unit"
        val currency = "MXN"
        val unitPrice = 1500
        val quantity = 1.0

        val brand = Brand(1L, "Test Brand")
        val category = Category(1L, "Test Category")
        val compositeShoppingItem = CompositeShoppingItem(
            shoppingItemId, itemId, 1, itemName,
            unitPrice, currency, 1.0, unit,
            brand, category
        )

        //when
        val request = compositeShoppingItem.toRequest()

        //then
        assertNotNull(request)
        assertEquals(shoppingItemId, request.mobileId)
        assertEquals(itemId, request.mobileItemId)
        assertNull(request.id)

        assertNotNull(request.brand)
        assertNotNull(request.category)

        assertEquals(itemName, request.name)
        assertEquals(unit, request.unit)
        assertEquals(unitPrice, request.unitPrice)
        assertEquals(quantity, request.quantity, .001)
        assertEquals(currency, request.currency)
    }

    @Test
    fun transform_toRequest_existingItem() {
        //given
        val shoppingItemId = 10L
        val itemId = 20L
        val itemName = "Android Test Item"
        val unit = "Unit"
        val currency = "MXN"
        val unitPrice = 1500
        val quantity = 1.0

        val brand = Brand(1L, "Test Brand")
        val category = Category(1L, "Test Category")
        val compositeShoppingItem = CompositeShoppingItem(
            shoppingItemId, itemId, 0, itemName,
            unitPrice, currency, 1.0, unit,
            brand, category
        )

        //when
        val request = compositeShoppingItem.toRequest()

        //then
        assertNotNull(request)
        assertEquals(shoppingItemId, request.mobileId)
        assertNull(request.mobileItemId)
        assertEquals(itemId, request.id)

        assertNotNull(request.brand)
        assertNotNull(request.category)

        assertEquals(itemName, request.name)
        assertEquals(unit, request.unit)
        assertEquals(unitPrice, request.unitPrice)
        assertEquals(quantity, request.quantity, .001)
        assertEquals(currency, request.currency)
    }

    @Test
    fun transform_toRequest_existingItem_noBrand() {
        //given
        val shoppingItemId = 10L
        val itemId = 20L
        val itemName = "Android Test Item"
        val unit = "Unit"
        val currency = "MXN"
        val unitPrice = 1500
        val quantity = 1.0

        val category = Category(1L, "Test Category")
        val compositeShoppingItem = CompositeShoppingItem(
            shoppingItemId, itemId, 0, itemName,
            unitPrice, currency, 1.0, unit,
            null, category
        )

        //when
        val request = compositeShoppingItem.toRequest()

        //then
        assertNotNull(request)
        assertEquals(shoppingItemId, request.mobileId)
        assertNull(request.mobileItemId)
        assertEquals(itemId, request.id)

        assertNull(request.brand)
        assertNotNull(request.category)

        assertEquals(itemName, request.name)
        assertEquals(unit, request.unit)
        assertEquals(unitPrice, request.unitPrice)
        assertEquals(quantity, request.quantity, .001)
        assertEquals(currency, request.currency)
    }

    @Test
    fun transform_toRequest_newItem_noBrand() {
        //given
        val shoppingItemId = 10L
        val itemId = 20L
        val itemName = "Android Test Item"
        val unit = "Unit"
        val currency = "MXN"
        val unitPrice = 1500
        val quantity = 1.0

        val category = Category(1L, "Test Category")
        val compositeShoppingItem = CompositeShoppingItem(
            shoppingItemId, itemId, 1, itemName,
            unitPrice, currency, 1.0, unit,
            null, category
        )

        //when
        val request = compositeShoppingItem.toRequest()

        //then
        assertNotNull(request)
        assertEquals(shoppingItemId, request.mobileId)
        assertEquals(itemId, request.mobileItemId)
        assertNull(request.id)

        assertNull(request.brand)
        assertNotNull(request.category)

        assertEquals(itemName, request.name)
        assertEquals(unit, request.unit)
        assertEquals(unitPrice, request.unitPrice)
        assertEquals(quantity, request.quantity, .001)
        assertEquals(currency, request.currency)
    }

    @Test(expected = IllegalArgumentException::class)
    fun should_throwException_when_shoppingItemListIsNull() {
        //given
        val store = Store(1L, "Test Store")
        val compositeShoppingList = CompositeShoppingList(1L, LocalDate.now(), store)

        //when
        compositeShoppingList.toRequest()

        //then expect exception
    }

    @Test(expected = IllegalArgumentException::class)
    fun should_throwException_when_shoppingItemListIsEmpty() {
        //given
        val store = Store(1L, "Test Store")
        val compositeShoppingList = CompositeShoppingList(1L, LocalDate.now(), store)
        compositeShoppingList.shoppingItems = listOf()

        //when
        compositeShoppingList.toRequest()

        //then expect exception
    }
}