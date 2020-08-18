package mx.kinich49.itemtracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import mx.kinich49.itemtracker.shoppigList.ShoppingListActivity
import kotlinx.android.synthetic.main.activity_main.*
import mx.kinich49.itemtracker.remote.SchedulerProvider
import mx.kinich49.itemtracker.remote.impl.SchedulerProviderImpl
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        add_shopping_list.setOnClickListener {
            val intent = Intent(this, ShoppingListActivity::class.java)
            startActivity(intent)
        }

//        App.getDownloadSync(this)
//            .downloadData()
//            .subscribeOn(SchedulerProvider.DEFAULT.networkScheduler())
//            .observeOn(SchedulerProvider.DEFAULT.mainScheduler())
//            .subscribe(object : CompletableObserver {
//                override fun onComplete() {
//                    Timber.d("On Complete")
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onError(e: Throwable) {
//                }
//
//            })
    }
}