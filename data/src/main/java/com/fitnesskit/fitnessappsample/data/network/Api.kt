package com.fitnesskit.fitnessappsample.data.network

import com.fitnesskit.domain.modules.FitnessSchedule
import com.fitnesskit.domain.modules.Tabs
import com.fitnesskit.domain.modules.Trainers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/schedule/get_v3/?club_id=2")
    suspend fun getAllSchedule(): FitnessSchedule?

}