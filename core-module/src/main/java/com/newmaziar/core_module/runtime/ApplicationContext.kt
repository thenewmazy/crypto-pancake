package com.newmaziar.core_module.runtime

import android.annotation.SuppressLint
import android.content.Context


@SuppressLint("StaticFieldLeak")
object ApplicationContext {
    private var context: Context? = null
    val appContext: Context
        get() = context ?: throw IllegalStateException("Application context not initialized")

    @JvmStatic
    fun initialize(appContext: Context) {
        context = appContext.applicationContext

    }
}