package com.justjump.domain.tweets

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TweetsDataModel(
    val user: String,
    val text: String,
    val location_latitude: Double,
    val location_longitude: Double,
    val id: String
): Parcelable