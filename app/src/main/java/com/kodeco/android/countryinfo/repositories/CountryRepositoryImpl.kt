package com.kodeco.android.countryinfo.repositories

import com.kodeco.android.countryinfo.api.CountryAPIService
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.models.CountryName
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountryRepositoryImpl(private val service: CountryAPIService) : CountryRepository {
    private var countries = mutableListOf<Country>()
    override fun fetchCountries(): Flow<List<Country>> = flow {
        delay(3000)
        val countriesResponse = service.getAllCountries()

        countries.clear()
        countries.addAll(
            if (countriesResponse.isSuccessful) {
                countriesResponse.body()!!
            } else {
                throw Throwable("Request failed: ${countriesResponse.message()}")
            }
        )

        emit(countries)
    }

    override fun getCountry(index: Int): Country? {
        return countries.elementAt(index)
    }
}