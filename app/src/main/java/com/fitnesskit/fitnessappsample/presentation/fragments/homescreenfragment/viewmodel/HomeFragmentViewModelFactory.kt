package com.fitnesskit.fitnessappsample.presentation.fragments.homescreenfragment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fitnesskit.domain.ScheduleInteractor
import javax.inject.Inject

class HomeFragmentViewModelFactory @Inject constructor(val scheduleInteractor: ScheduleInteractor): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeFragmentViewModel(scheduleInteractor) as T
    }
}