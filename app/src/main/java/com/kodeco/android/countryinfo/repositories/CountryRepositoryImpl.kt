package com.kodeco.android.countryinfo.repositories

import com.kodeco.android.countryinfo.api.CountryAPIService
import com.kodeco.android.countryinfo.models.Country
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CountryRepositoryImpl(
    private val service: CountryAPIService,
) : CountryRepository {
    private var favorites = setOf<String>()

    private val _countries: MutableStateFlow<List<Country>> = MutableStateFlow(emptyList())
    override val countries: StateFlow<List<Country>> = _countries.asStateFlow()
    override suspend fun fetchCountries() {
        val countriesResponse = service.getAllCountries()

        _countries.value = emptyList()
        _countries.value = try {
            if (countriesResponse.isSuccessful) {
                countriesResponse.body()!!
                    .toMutableList()
                    .map { country ->
                        country.copy(isFavorite = favorites.contains(country.commonName))
                    }
            } else {
                throw Throwable("Request failed: ${countriesResponse.message()}")
            }
        } catch (e: Exception) {
            throw Throwable("Request failed: ${e.message}")
        }
    }

    override fun getCountry(index: Int): Country? =
        _countries.value.getOrNull(index)

    override fun favorite(country: Country) {
        favorites = if (favorites.contains(country.commonName)) {
            favorites - country.commonName
        } else {
            favorites + country.commonName
        }
        val index = _countries.value.indexOf(country)
        val mutableCountries = _countries.value.toMutableList()
        mutableCountries[index] =
            mutableCountries[index].copy(isFavorite = favorites.contains(country.commonName))
        _countries.value = mutableCountries.toList()
    }
}