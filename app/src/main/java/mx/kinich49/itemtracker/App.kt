package mx.kinich49.itemtracker

import android.app.Application
import android.util.Base64
import androidx.work.Configuration
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import mx.kinich49.itemtracker.entities.apis.interceptors.AuthorizationInterceptor
import mx.kinich49.itemtracker.entities.apis.services.*
import mx.kinich49.itemtracker.entities.apis.typeadapters.LocalDateTypeAdapter
import mx.kinich49.itemtracker.entities.database.ItemTrackerDatabase
import mx.kinich49.itemtracker.entities.database.daos.*
import mx.kinich49.itemtracker.entities.usecases.sync.downstream.*
import mx.kinich49.itemtracker.entities.usecases.sync.upstream.LoadPendingShoppingListUseCase
import mx.kinich49.itemtracker.entities.usecases.sync.upstream.ShoppingListSyncUseCase
import mx.kinich49.itemtracker.entities.usecases.sync.upstream.UpdateLocalShoppingListUseCase
import mx.kinich49.itemtracker.entities.usecases.sync.upstream.UploadPendingShoppingListUseCase
import mx.kinich49.itemtracker.features.factories.ItemTrackerWorkerFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.time.LocalDate


class App : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this);
        }

    }

    override fun getWorkManagerConfiguration(): Configuration {
        val db = ItemTrackerDatabase.getDatabase(this)
        val loadUseCase = getLoadPendingShoppingListUseCase(
            db.shoppingListDao(),
            db.shoppingItemDao()
        )

        val uploadUseCase = getUploadPendingShoppingListUseCase(shoppingListService)

        val updateUseCase = getUpdateLocalShoppingListUseCase(
            db.storeDao(), db.shoppingListDao(), db.brandDao(), db.categoryDao(),
            db.itemDao(), db.shoppingItemDao()
        )
        val upstreamSync = getShoppingListSyncUseCase(
            loadUseCase, uploadUseCase, updateUseCase
        )

        val brandUseCase = DownloadBrandsUseCase(brandService, db.brandDao())
        val categoriesUseCase = DownloadCategoriesUseCase(categoryService, db.categoryDao())
        val storesUseCase = DownloadStoresUseCase(storeService, db.storeDao())
        val itemsUseCase = DownloadItemsUseCase(itemService, db.itemDao())

        val downstreamSync = getDownstreamSync(
            brandUseCase, categoriesUseCase,
            storesUseCase, itemsUseCase
        )

        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(ItemTrackerWorkerFactory(upstreamSync, downstreamSync))
            .build()
    }

    companion object ServiceFactory {

        val brandService: BrandService by lazy {
            retrofit
                .create(BrandService::class.java)
        }

        val categoryService: CategoryService by lazy {
            retrofit
                .create(CategoryService::class.java)
        }

        val storeService: StoreService by lazy {
            retrofit
                .create(StoreService::class.java)
        }

        val itemService: ItemService by lazy {
            retrofit
                .create(ItemService::class.java)
        }

        val shoppingItemService: ShoppingItemService by lazy {
            retrofit
                .create(ShoppingItemService::class.java)
        }

        val shoppingListService: ShoppingListService by lazy {
            retrofit
                .create(ShoppingListService::class.java)
        }

        fun getDownstreamSync(
            brandsUseCase: DownloadBrandsUseCase,
            categoriesUseCase: DownloadCategoriesUseCase,
            storesUseCase: DownloadStoresUseCase,
            itemsUseCase: DownloadItemsUseCase
        ) = DownstreamSynUseCase(
            brandsUseCase, categoriesUseCase, storesUseCase, itemsUseCase
        )


        fun getShoppingListSyncUseCase(
            loadPendingShoppingListUseCase: LoadPendingShoppingListUseCase,
            uploadPendingShoppingListUseCase: UploadPendingShoppingListUseCase,
            updateLocalShoppingListUseCase: UpdateLocalShoppingListUseCase
        ) = ShoppingListSyncUseCase(
            loadPendingShoppingListUseCase,
            uploadPendingShoppingListUseCase,
            updateLocalShoppingListUseCase
        )


        fun getLoadPendingShoppingListUseCase(
            shoppingListDao: ShoppingListDao,
            shoppingItemDao: ShoppingItemDao
        ) = LoadPendingShoppingListUseCase(shoppingListDao, shoppingItemDao)


        fun getUploadPendingShoppingListUseCase(shoppingListService: ShoppingListService) =
            UploadPendingShoppingListUseCase(shoppingListService)

        fun getUpdateLocalShoppingListUseCase(
            storeDao: StoreDao,
            shoppingListDao: ShoppingListDao, brandDao: BrandDao, categoryDao: CategoryDao,
            itemDao: ItemDao, shoppingItemDao: ShoppingItemDao
        ) =
            UpdateLocalShoppingListUseCase(
                storeDao, shoppingListDao, brandDao, categoryDao, itemDao, shoppingItemDao
            )


        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        }

        private val okHttpClient: OkHttpClient by lazy {
            // TODO("Credentials should be injected")
            val httpInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Timber.tag("Network").d(message);
                }
            })

            httpInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

            val credentials = "${BuildConfig.USERNAME}:${BuildConfig.PASSWORD}"

            OkHttpClient.Builder()
                .addInterceptor(httpInterceptor)
                .addInterceptor(
                    AuthorizationInterceptor(
                        "Basic ${
                            Base64.encodeToString(
                                credentials.toByteArray(),
                                Base64.NO_WRAP
                            )
                        }"
                    )
                )
                .addNetworkInterceptor(StethoInterceptor())
                .build();
        }

        private val gson: Gson by lazy {
            GsonBuilder()
                .registerTypeAdapter(
                    LocalDate::class.java,
                    LocalDateTypeAdapter().nullSafe()
                )
                .create()
        }

    }
}