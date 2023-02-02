package com.fitnesskit.fitnessappsample.presentation.fragments.homescreenfragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fitnesskit.domain.ScheduleInteractor
import com.fitnesskit.domain.modules.FitnessSchedule
import com.fitnesskit.fitnessappsample.presentation.fragments.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragmentViewModel(private val scheduleInteractor: ScheduleInteractor): BaseViewModel() {

    private val _fitnessScheduleMutableLiveData: MutableLiveData<List<FitnessSchedule>> = MutableLiveData()
    val fitnessScheduleLiveData: LiveData<List<FitnessSchedule>> = _fitnessScheduleMutableLiveData

    private val _isErrorMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isErrorLiveData: LiveData<Boolean> = _isErrorMutableLiveData

    private val _isLoadingMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingMutableLiveData

    fun loadFitnessSchedule() {
        isLoadingLiveData(true)
        viewModelScope.launch {
            val charactersEntityResult = scheduleInteractor.getFitnessSchedule()
            if (charactersEntityResult != null) {
                updateAppropriateLiveData(listOf(charactersEntityResult), _fitnessScheduleMutableLiveData)
            } else {
                onResultError()
            }
        }
    }

    private fun updateAppropriateLiveData(trainers: List<FitnessSchedule>, mutableLiveData: MutableLiveData<List<FitnessSchedule>>) {
        if (!trainers.isNullOrEmpty()) {
            onResultSuccess(trainers, mutableLiveData)
        } else {
            onResultError()
        }
    }

    private fun onResultSuccess(
        trainers: List<FitnessSchedule>,
        mutableLiveData: MutableLiveData<List<FitnessSchedule>>,
    ) {
        if (!mutableLiveData.value.isNullOrEmpty()) {
            mutableLiveData.value =
                mutableLiveData.value?.plus(trainers)
        } else {
            mutableLiveData.value = trainers
        }
        isLoadingLiveData(false)
    }

    private fun onResultError() {
        viewModelScope.launch {
            delay(300)
            isLoadingLiveData(false)
        }.invokeOnCompletion {
            _isErrorMutableLiveData.value = true
        }
    }

    private fun isLoadingLiveData(isLoading: Boolean) {
        this._isLoadingMutableLiveData.value = isLoading
    }

}