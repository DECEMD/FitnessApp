package com.fitnesskit.domain.modules

import com.google.gson.annotations.SerializedName

data class Trainers(
    @SerializedName("id")
    val id: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("position")
    val position: String,
    @SerializedName("image_url")
    val imageURL: String,
    @SerializedName("image_url_small")
    val imageURLSmall: String,
    @SerializedName("image_url_medium")
    val imageURLMedium: String,
    @SerializedName("description")
    val description: String?
)
