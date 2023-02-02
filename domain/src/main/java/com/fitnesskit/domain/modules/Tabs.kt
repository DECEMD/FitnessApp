package com.fitnesskit.domain.modules

import com.google.gson.annotations.SerializedName

data class Tabs(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)
