package mx.kinich49.itemtracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mx.kinich49.itemtracker.shoppigList.ShoppingListActivity
import kotlinx.android.synthetic.main.activity_main.*

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
//            .subscribeOn(SchedulerProvider.DEFAULT_NETWORK)
//            .observeOn(SchedulerProvider.DEFAULT_MAIN)
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