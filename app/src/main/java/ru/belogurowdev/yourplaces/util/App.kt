package ru.belogurowdev.yourplaces.util

import android.app.Application
import android.content.Context
import android.util.Log

import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher


/**
 * Created by alexbelogurow on 26.08.17.
 */

class App : Application() {

    private var mRefWatcher: RefWatcher? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "create")
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        mRefWatcher = LeakCanary.install(this)
    }

    companion object {

        const val BASE_URL = "https://maps.googleapis.com"
        const val API_KEY = "AIzaSyAuJIEnY4TcR-G67YJSgS2CNbPJNABzs3s"

        private val TAG = "APP"

        fun getRefWatcher(context: Context): RefWatcher? {
            val application = context.applicationContext as App
            return application.mRefWatcher
        }
    }

}
