package com.example.treescompose.data.remote.responses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fields(
    var adresse: String? = null,
    val arrondissement: String? = null,
    val circonferenceencm: Int? = null,
    val complementadresse: String? = null,
    val domanialite: String? = null,
    val espece: String? = null,
    val genre: String? = null,
    val geo_point_2d: List<Double>? = null,
    var hauteurenm: Int? = null,
    val idbase: Int? = null,
    val idemplacement: String? = null,
    val libellefrancais: String? = null,
    val remarquable: String? = null,
    val stadedeveloppement: String? = null,
    val typeemplacement: String? = null,
    val varieteoucultivar: String? = null,
    var image: String? = null
): Parcelable