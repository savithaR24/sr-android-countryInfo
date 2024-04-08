package com.kodeco.android.countryinfo.models

data class Country (
    val commonName: String,
    val mainCapital: String,
    val population: Long,
    val area: Double,
    val flagUrl: String,
    val isFavorite: Boolean = false,
)
