package com.newmaziar.cryptopancake

import android.app.Application
import com.newmaziar.core_module.runtime.ApplicationContext
import com.newmaziar.cryptopancake.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CryptoPancakeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ApplicationContext.initialize(this.applicationContext)
        startKoin {
            androidLogger()
            androidContext(this@CryptoPancakeApplication)
            modules(appModule)
        }
    }
}