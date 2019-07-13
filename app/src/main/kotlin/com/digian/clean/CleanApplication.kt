package com.digian.clean

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Created by Alex Forrester on 2019-05-02.
 */
class CleanApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Timber.d("Adding Koin modules to our application")
        startKoin {
            if (BuildConfig.DEBUG) {
                // use Koin logger
                printLogger()
            }

            androidContext(this@CleanApplication)
            modules(appModules)
        }
    }
}
