package com.example.verygoodtaskplanner

import android.app.Application
import com.example.verygoodtaskplanner.di.Modules.repositories
import com.example.verygoodtaskplanner.di.Modules.router
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(router,repositories))
        }
    }
}