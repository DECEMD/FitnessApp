package com.fitnesskit.domain.modules

import com.google.gson.annotations.SerializedName

data class Lessons(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("place")
    val place: String,
    @SerializedName("coach_id")
    val coachId: String,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("endTime")
    val endTime: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("appointment_id")
    val appointmentId: String,
    @SerializedName("service_id")
    val serviceId: String,
    @SerializedName("available_slots")
    val availableSlots: String,
    @SerializedName("commercial")
    val commercial: Boolean,
    @SerializedName("client_recorded")
    val clientRecorded: Boolean,
    @SerializedName("is_cancelled")
    val isCancelled: Boolean,
    @SerializedName("tab")
    val tab: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("tab_id")
    val tabId: String,
    var coachName: String = ""
)