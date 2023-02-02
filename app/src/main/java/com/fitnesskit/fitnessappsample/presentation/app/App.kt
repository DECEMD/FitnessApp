package com.fitnesskit.fitnessappsample.presentation.app

import android.app.Application
import com.fitnesskit.fitnessappsample.presentation.di.AppComponent
import com.fitnesskit.fitnessappsample.presentation.di.AppModule
import com.fitnesskit.fitnessappsample.presentation.di.DaggerAppComponent
import com.fitnesskit.fitnessappsample.presentation.di.DataModule

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .dataModule(DataModule())
            .build()
    }
}