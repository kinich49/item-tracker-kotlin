package com.kinich49.itemtracker

import android.app.Application
import android.content.Context
import android.util.Base64
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kinich49.itemtracker.models.sync.DBDownloadSync
import com.kinich49.itemtracker.remote.*
import com.kinich49.itemtracker.remote.deserializers.LocalDateDeserializer
import com.kinich49.itemtracker.remote.interceptors.AuthorizationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.time.LocalDate


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this);
        }

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

        fun getDownloadSync(context: Context): DBDownloadSync {
            return DBDownloadSync(
                brandService, categoryService, itemService,
                storeService, shoppingListService, shoppingItemService,
                ItemTrackerDatabase.getDatabase(context).brandDao(),
                ItemTrackerDatabase.getDatabase(context).categoryDao(),
                ItemTrackerDatabase.getDatabase(context).itemDao(),
                ItemTrackerDatabase.getDatabase(context).storeDao(),
                ItemTrackerDatabase.getDatabase(context).shoppingListDao(),
                ItemTrackerDatabase.getDatabase(context).shoppingItemDao()
            )
        }

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
                        "Basic ${Base64.encodeToString(
                            credentials.toByteArray(),
                            Base64.NO_WRAP
                        )}"
                    )
                )
                .addNetworkInterceptor(StethoInterceptor())
                .build();
        }

        private val gson: Gson by lazy {
            GsonBuilder()
                .registerTypeAdapter(
                    LocalDate::class.java,
                    LocalDateDeserializer()
                )
                .create()
        }

    }
}