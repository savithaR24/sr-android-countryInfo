package com.kodeco.android.countryinfo.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryDto(
    val name: CountryNameDto,
    val capital: List<String>?,
    val population: Long,
    val area: Double,
    val flags: CountryFlagsDto,
    val isFavorite: Boolean = false,
) {
    val commonName = name.common
    val flagUrl = flags.png
    val nonNullCapital = capital?.firstOrNull() ?: "N/A"
}
