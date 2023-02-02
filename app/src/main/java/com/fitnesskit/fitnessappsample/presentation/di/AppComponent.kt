package com.fitnesskit.fitnessappsample.presentation.di

import android.content.Context
import com.fitnesskit.fitnessappsample.presentation.fragments.homescreenfragment.HomeScreenFragment
import com.fitnesskit.fitnessappsample.presentation.fragments.homescreenfragment.di.HomeScreenFragmentComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {

    val context: Context

    val homeScreenFragment: HomeScreenFragmentComponent

}