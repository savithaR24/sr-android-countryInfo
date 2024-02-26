package com.kodeco.android.countryinfo.models

data class Country(
    val name: CountryName,
    val capital: List<String>?,
    val population: Long,
    val area: Double,
    val flags: CountryFlags,
) {
    val commonName = name.common
    val flagUrl = flags.png
}
