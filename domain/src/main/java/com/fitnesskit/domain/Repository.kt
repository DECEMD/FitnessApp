package com.fitnesskit.domain

import com.fitnesskit.domain.modules.FitnessSchedule

interface Repository {

    suspend fun getSchedule(): FitnessSchedule?

}