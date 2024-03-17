package com.kodeco.android.countryinfo.repositories

import com.kodeco.android.countryinfo.models.Country
import kotlinx.coroutines.flow.Flow

class CountryRepositoryImpl: CountryRepository {
    private val countries: List<Country> = TODO()
    override fun fetchCountries(): Flow<List<Country>> {
        TODO("Not yet implemented")
    }

    override fun getCountry(index: Int): Country? {
        TODO("Not yet implemented")
    }
}