package com.kodeco.android.countryinfo.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Country(
    val name: CountryName,
    val capital: List<String>?,
    val population: Long,
    val area: Double,
    val flags: CountryFlags,
) {
    val commonName = name.common
    val flagUrl = flags.png
    val nonNullCapital = capital?.firstOrNull() ?: "N/A"
}
