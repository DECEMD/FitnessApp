package com.fitnesskit.fitnessappsample.data

import android.icu.text.IDNA
import android.util.Log
import com.fitnesskit.domain.Repository
import com.fitnesskit.domain.modules.FitnessSchedule
import com.fitnesskit.domain.modules.Lessons
import com.fitnesskit.domain.modules.Option
import com.fitnesskit.domain.modules.Tabs
import com.fitnesskit.domain.modules.Trainers
import com.fitnesskit.fitnessappsample.data.network.Api
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    private val fitnessApi: Api): Repository {

    override suspend fun getSchedule() = try {
        val res = fitnessApi.getAllSchedule()
        res
    } catch (e: Exception) {
        Log.d("log", e.toString())
        null
    }
}