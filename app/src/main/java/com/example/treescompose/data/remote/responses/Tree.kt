package com.example.treescompose.data.remote.responses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tree(
    val datasetid: String,
    val fields: Fields,
    val geometry: Geometry? = null,
    val record_timestamp: String? = null,
    val recordid: String
): Parcelable