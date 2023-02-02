package com.fitnesskit.domain.modules

import com.google.gson.annotations.SerializedName

data class FitnessSchedule(
    @field:SerializedName("trainers")
    val trainers: List<Trainers>,
    @field:SerializedName("tabs")
    val tabs: List<Tabs>,
    @field:SerializedName("lessons")
    val lessons: List<Lessons>,
    @field:SerializedName("option")
    val option: Option
)