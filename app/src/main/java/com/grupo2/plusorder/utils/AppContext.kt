package com.grupo2.plusorder.utils

import android.app.Application
import android.content.Context

object AppContext : Application() {
    var appContext: Context = applicationContext

    override fun onCreate() {
        super.onCreate()
        this.appContext = applicationContext
    }
}