package com.kodeco.android.countryinfo.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class Country (
    @PrimaryKey
    val commonName: String,
    val mainCapital: String,
    val population: Long,
    val area: Double,
    val flagUrl: String,
    val isFavorite: Boolean = false,
)
