package com.example.treescompose.data.remote.responses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fields(
    var adresse: String?,
    val arrondissement: String?,
    val circonferenceencm: Int?,
    val complementadresse: String?,
    val domanialite: String?,
    val espece: String?,
    val genre: String?,
    val geo_point_2d: List<Double>?,
    var hauteurenm: Int?,
    val idbase: Int?,
    val idemplacement: String?,
    val libellefrancais: String?,
    val remarquable: String?,
    val stadedeveloppement: String?,
    val typeemplacement: String?,
    val varieteoucultivar: String?
): Parcelable