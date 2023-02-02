package com.fitnesskit.domain

import com.fitnesskit.domain.modules.FitnessSchedule
import javax.inject.Inject

interface ScheduleInteractorInterface {

    suspend fun getFitnessSchedule(): FitnessSchedule?

}

class ScheduleInteractor @Inject constructor(private val repository: Repository) : ScheduleInteractorInterface {

    override suspend fun getFitnessSchedule() = repository.getSchedule()

}