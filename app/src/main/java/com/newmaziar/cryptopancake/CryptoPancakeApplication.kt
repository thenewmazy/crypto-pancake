package com.newmaziar.cryptopancake

import android.app.Application
import com.newmaziar.cryptopancake.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CryptoPancakeApplication : Application() {

    companion object {
        fun getApplicationContext() = CryptoPancakeApplication().applicationContext
    }
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CryptoPancakeApplication)
            modules(appModule)
        }
    }
}