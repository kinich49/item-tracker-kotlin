package mx.kinich49.itemtracker.models.sync;


import androidx.core.util.Pair;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import mx.kinich49.itemtracker.models.database.Brand;
import mx.kinich49.itemtracker.models.database.Category;
import mx.kinich49.itemtracker.models.database.Item;
import mx.kinich49.itemtracker.models.database.ShoppingItem;
import mx.kinich49.itemtracker.models.database.ShoppingList;
import mx.kinich49.itemtracker.models.database.Store;
import mx.kinich49.itemtracker.models.database.daos.BrandDao;
import mx.kinich49.itemtracker.models.database.daos.CategoryDao;
import mx.kinich49.itemtracker.models.database.daos.ItemDao;
import mx.kinich49.itemtracker.models.database.daos.ShoppingItemDao;
import mx.kinich49.itemtracker.models.database.daos.ShoppingListDao;
import mx.kinich49.itemtracker.models.database.daos.StoreDao;
import mx.kinich49.itemtracker.models.database.relations.CompositeShoppingItem;
import mx.kinich49.itemtracker.models.database.relations.CompositeShoppingList;
import mx.kinich49.itemtracker.remote.JsonApi;
import mx.kinich49.itemtracker.remote.ShoppingListService;
import mx.kinich49.itemtracker.remote.requests.ShoppingListRequest;
import mx.kinich49.itemtracker.remote.response.BrandResponse;
import mx.kinich49.itemtracker.remote.response.CategoryResponse;
import mx.kinich49.itemtracker.remote.response.ItemResponse;
import mx.kinich49.itemtracker.remote.response.ShoppingItemResponse;
import mx.kinich49.itemtracker.remote.response.ShoppingListResponse;
import mx.kinich49.itemtracker.remote.response.StoreResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpstreamSyncTest {

    @Mock
    ShoppingListDao shoppingListDao;
    @Mock
    ShoppingItemDao shoppingItemDao;
    @Mock
    ItemDao itemDao;
    @Mock
    BrandDao brandDao;
    @Mock
    CategoryDao categoryDao;
    @Mock
    StoreDao storeDao;
    @Mock
    ShoppingListService shoppingListService;
    @InjectMocks
    UpstreamSync subject;

    /**
     * This test tries to retrieve all pending shopping lists (state is 1)
     * <p>
     * ShoppingList, ShoppingItem, Item, Brand, Category and Store
     * do not previously exist in centralized DB.
     */
    @Test
    public void shouldSyncPendingList() {
        //given
        Pair<CompositeShoppingList, ShoppingListResponse> data = getData();
        List<CompositeShoppingList> compositeShoppingLists = new ArrayList<>();
        compositeShoppingLists.add(data.first);

        when(shoppingListDao.getPendingShoppingLists())
                .thenReturn(compositeShoppingLists);

        when(shoppingListService.postShoppingList(any(ShoppingListRequest.class)))
                .thenReturn(Single.just(new JsonApi<>(data.second)));

        //when
        TestObserver<Void> testObserver = subject.uploadData()
                .test();

        //then
        testObserver.assertSubscribed();
        testObserver.assertComplete();
    }

    /**
     * This test tires to map a shopping List to Shoppging List Request
     */
    @Test
    public void shouldMapShoppingListToRequest() {
        //Given
        Long storeId = 19L;
        long shoppingListId = 20L;
        long itemId = 21L;
        Long brandId = 22L;
        Long categoryId = 23L;
        long shoppingItemId = 24L;

        String storeName = "Android Test Store";
        String itemName = "Android Test Item";
        String brandName = "Android Test Brand";
        String categoryName = "Android Test Category";

        LocalDate shoppingDate = LocalDate.now();
        double quantity = 1.0;
        int unitPrice = 1500;
        String currency = "MXN";
        String unit = "Unit";

        Store store = new Store(storeId, storeName, 1);
        Brand brand = new Brand(brandId, brandName, 1);
        Category category = new Category(categoryId, categoryName, 1);

        CompositeShoppingItem compositeShoppingItem = new CompositeShoppingItem(
                shoppingItemId, itemId, 1, itemName, unitPrice, currency,
                quantity, unit, brand, category);

        List<CompositeShoppingItem> compositeShoppingItems = new ArrayList<>();
        compositeShoppingItems.add(compositeShoppingItem);
        CompositeShoppingList compositeShoppingList = new CompositeShoppingList(shoppingListId,
                shoppingDate, store);
        compositeShoppingList.setShoppingItems(compositeShoppingItems);

        //when
        TestObserver<ShoppingListRequest> result = subject.mapEntityToRequest(compositeShoppingList)
                .test();

        //then
        result.assertSubscribed()
                .assertNoErrors()
                .assertComplete()
                .assertValueCount(1);
    }

    /**
     * This test tries to update the local DB.
     * Store, Brand, Category, Item, ShoppingItem, ShoppingItemList do not
     * exist previously in centralized DB.
     */
    @Test
    public void should_updateLocalDB_when_nothingExistPreviously() {
        final long storeId = 10L;
        final long brandId = 20L;
        final long categoryId = 30L;
        final long itemId = 40L;
        final long shoppingItemId = 50L;
        final long shoppingListId = 60L;

        final long storeMobileId = 100L;
        final long brandMobileId = 200L;
        final long categoryMobileId = 300L;
        final long itemMobileId = 400L;
        final long shoppingItemMobileId = 500L;
        final long shoppingListMobileId = 600L;

        final String storeName = "Android Test Store";
        final String brandName = "Android Test Brand";
        final String categoryName = "Android Test Category";
        final String itemName = "Android Test Item";

        final LocalDate shoppingDate = LocalDate.now();

        StoreResponse storeResponse = new StoreResponse(storeId, storeMobileId, storeName);
        CategoryResponse categoryResponse = new CategoryResponse(categoryId, categoryMobileId, categoryName);
        BrandResponse brandResponse = new BrandResponse(brandId, brandMobileId, brandName);

        ItemResponse itemResponse = new ItemResponse(itemId, itemMobileId, itemName, brandResponse, categoryResponse);
        ShoppingItemResponse shoppingItemResponse = new ShoppingItemResponse(shoppingItemId, shoppingItemMobileId,
                "MXN", 1.0, "Unit", 1500, shoppingListId, itemResponse);

        List<ShoppingItemResponse> shoppingItemResponses = new ArrayList<>();
        shoppingItemResponses.add(shoppingItemResponse);

        ShoppingListResponse shoppingListResponse = new ShoppingListResponse(shoppingListId, shoppingListMobileId,
                shoppingDate, storeResponse, shoppingItemResponses);

        //when
        TestObserver<Void> result = subject.updateLocalDB(shoppingListResponse)
                .test();

        result.assertSubscribed()
                .assertComplete();

        //Verify everything got inactive
        verify(brandDao).inactivate(eq(brandMobileId));
        verify(storeDao).inactivate(eq(storeMobileId));
        verify(categoryDao).inactivate(eq(categoryMobileId));
        verify(itemDao).inactivate(eq(itemMobileId));
        verify(shoppingItemDao).inactivate(eq(shoppingItemMobileId));
        verify(shoppingItemDao).inactivate(eq(shoppingItemMobileId));

        //verify everything got inserted
        verify(brandDao).insert(any(Brand.class));
        verify(storeDao).insert(any(Store.class));
        verify(categoryDao).insert(any(Category.class));
        verify(itemDao).insert(any(Item.class));
        verify(shoppingListDao).insert(any(ShoppingList.class));
        verify(shoppingItemDao).insert(any(ShoppingItem.class));
    }

    /**
     * This test tries to update local DB
     * <p>
     * Brand, Store and Category exist previously in local/centralized DB
     * <p>
     * ShoppingList, ShoppingItem and Item do not exist previously in
     * centralized DB
     */
    @Test
    public void should_updateLocalDB_when_BrandAndCategoryAndStore_existPreviously() {
        final long storeId = 10L;
        final long brandId = 20L;
        final long categoryId = 30L;
        final long itemId = 40L;
        final long shoppingItemId = 50L;
        final long shoppingListId = 60L;

        final long itemMobileId = 400L;
        final long shoppingItemMobileId = 500L;
        final long shoppingListMobileId = 600L;

        final String storeName = "Test Store";
        final String brandName = "Test Brand";
        final String categoryName = "Test Category";
        final String itemName = "Android Test Item";

        final LocalDate shoppingDate = LocalDate.now();

        StoreResponse storeResponse = new StoreResponse(storeId, null, storeName);
        CategoryResponse categoryResponse = new CategoryResponse(categoryId, null, categoryName);
        BrandResponse brandResponse = new BrandResponse(brandId, null, brandName);

        ItemResponse itemResponse = new ItemResponse(itemId, itemMobileId, itemName, brandResponse, categoryResponse);
        ShoppingItemResponse shoppingItemResponse = new ShoppingItemResponse(shoppingItemId, shoppingItemMobileId,
                "MXN", 1.0, "Unit", 1500, shoppingListId, itemResponse);

        List<ShoppingItemResponse> shoppingItemResponses = new ArrayList<>();
        shoppingItemResponses.add(shoppingItemResponse);

        ShoppingListResponse shoppingListResponse = new ShoppingListResponse(shoppingListId, shoppingListMobileId,
                shoppingDate, storeResponse, shoppingItemResponses);

        //when
        TestObserver<Void> result = subject.updateLocalDB(shoppingListResponse)
                .test();

        result.assertSubscribed()
                .assertComplete();

        //Verify these had no interactions
        verifyNoInteractions(brandDao);
        verifyNoInteractions(storeDao);
        verifyNoInteractions(categoryDao);

        //verify these got inactive
        verify(shoppingListDao).inactivate(eq(shoppingListMobileId));
        verify(shoppingItemDao).inactivate(eq(shoppingItemMobileId));

        //verify
        verify(itemDao).insert(any(Item.class));
        verify(shoppingListDao).insert(any(ShoppingList.class));
        verify(shoppingItemDao).insert(any(ShoppingItem.class));
    }

    /**
     * This test tries to update the local DB.
     * <p>
     * The list has two shoppingItems.
     * One shopping Item has a Brand/Item that do not exist in local/centralized DB.
     * The second Item has a Brand/Item thad exist previously in local/centralized DB.
     * <p>
     * Store and Category exist previously
     */
    @Test
    public void should_updateLocalDB_when_ListHasTwoItems_withDifferentBrands() {
        //Given
        final long storeId = 10L;
        final long brandId_A = 20L;
        final long brandId_B = 21L;
        final long categoryId = 30L;
        final long itemId_A = 40L;
        final long itemId_B = 41L;
        final long shoppingItemId_A = 50L;
        final long shoppingItemId_B = 51L;
        final long shoppingListId = 60L;

        final long brandMobileId_A = 200L;
        final long itemMobileId_A = 400L;
        final long shoppingItemMobileId_A = 500L;
        final long shoppingItemMobileId_B = 501L;
        final long shoppingListMobileId = 600L;

        final String storeName = "Test Store";
        final String brandName_A = "Test Brand A";
        final String brandName_B = "Test Brand B";
        final String categoryName = "Test Category";
        final String itemName_A = "Android Test Item A";
        final String itemName_B = "Android Test Item B";

        final LocalDate shoppingDate = LocalDate.now();

        StoreResponse storeResponse = new StoreResponse(storeId, null, storeName);
        CategoryResponse categoryResponse = new CategoryResponse(categoryId, null, categoryName);
        BrandResponse brandResponse_A = new BrandResponse(brandId_A, brandMobileId_A, brandName_A);
        BrandResponse brandResponse_B = new BrandResponse(brandId_B, null, brandName_B);

        ItemResponse itemResponse_A = new ItemResponse(itemId_A, itemMobileId_A, itemName_A, brandResponse_A, categoryResponse);
        ShoppingItemResponse shoppingItemResponse_A = new ShoppingItemResponse(shoppingItemId_A, shoppingItemMobileId_A,
                "MXN", 1.0, "Unit", 1500, shoppingListId, itemResponse_A);

        ItemResponse itemResponse_B = new ItemResponse(itemId_B, null, itemName_B, brandResponse_B, categoryResponse);
        ShoppingItemResponse shoppingItemResponse_B = new ShoppingItemResponse(shoppingItemId_B, shoppingItemMobileId_B,
                "MXN", 1.0, "Unit", 2500, shoppingListId, itemResponse_B);

        List<ShoppingItemResponse> shoppingItemResponses = new ArrayList<>();
        shoppingItemResponses.add(shoppingItemResponse_A);
        shoppingItemResponses.add(shoppingItemResponse_B);

        ShoppingListResponse shoppingListResponse = new ShoppingListResponse(shoppingListId, shoppingListMobileId,
                shoppingDate, storeResponse, shoppingItemResponses);

        //when
        TestObserver<Void> result = subject.updateLocalDB(shoppingListResponse)
                .test();

        //then
        result.assertSubscribed()
                .assertComplete();

        verifyNoInteractions(storeDao);
        verifyNoInteractions(categoryDao);

        verify(brandDao).insert(any(Brand.class));
        verify(itemDao).insert(any(Item.class));
        verify(shoppingListDao).insert(any(ShoppingList.class));

        verify(brandDao).inactivate(eq(brandMobileId_A));
        verify(itemDao).inactivate(eq(itemMobileId_A));

        verify(shoppingItemDao).inactivate(eq(shoppingItemMobileId_A));
        verify(shoppingItemDao).inactivate(eq(shoppingItemMobileId_B));

        verify(shoppingItemDao, times(2)).insert(any(ShoppingItem.class));
    }

    private Pair<CompositeShoppingList, ShoppingListResponse> getData() {
        Long storeId = 19L;
        long shoppingListId = 20L;
        long itemId = 21L;
        Long brandId = 22L;
        Long categoryId = 23L;
        long shoppingItemId = 24L;

        String storeName = "Android Test Store";
        String itemName = "Android Test Item";
        String brandName = "Android Test Brand";
        String categoryName = "Android Test Category";

        Store store = new Store(storeId, storeName, 1);
        Brand brand = new Brand(brandId, brandName, 1);
        Category category = new Category(categoryId, categoryName, 1);

        CompositeShoppingItem compositeShoppingItem = new CompositeShoppingItem(
                shoppingItemId, itemId, 1, itemName, 1500, "MXN",
                1.0, "Unit", brand, category);

        CompositeShoppingList compositeShoppingList = new CompositeShoppingList(shoppingListId,
                LocalDate.now(), store);

        StoreResponse storeResponse = new StoreResponse(1L, storeId, storeName);
        BrandResponse brandResponse = new BrandResponse(1L, brandId, brandName);
        CategoryResponse categoryResponse = new CategoryResponse(1L, categoryId, categoryName);

        ItemResponse itemResponse = new ItemResponse(1L, itemId, itemName,
                brandResponse, categoryResponse);

        ShoppingItemResponse shoppingItemResponse = new ShoppingItemResponse(1L,
                shoppingItemId, "MXN", 1.0, "Unit", 1500,
                1L, itemResponse);

        List<ShoppingItemResponse> shoppingItemResponses = new ArrayList<>(1);
        shoppingItemResponses.add(shoppingItemResponse);

        ShoppingListResponse shoppingListResponse = new ShoppingListResponse(1L, shoppingListId,
                LocalDate.now(), storeResponse, shoppingItemResponses);

        return new Pair<>(compositeShoppingList, shoppingListResponse);
    }


}
