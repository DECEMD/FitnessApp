package com.fitnesskit.fitnessappsample.presentation.fragments.homescreenfragment.di

import com.fitnesskit.fitnessappsample.presentation.fragments.homescreenfragment.HomeScreenFragment
import dagger.Subcomponent

@Subcomponent
interface HomeScreenFragmentComponent {
    fun inject(homeScreenFragment: HomeScreenFragment)
}