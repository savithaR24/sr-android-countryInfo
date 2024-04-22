package com.kodeco.android.countryinfo.repositories

import com.kodeco.android.countryinfo.api.CountryAPIService
import com.kodeco.android.countryinfo.database.CountryDao
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.prefs.CountryPrefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CountryRepositoryImpl(
    private val service: CountryAPIService,
    private val countryDao: CountryDao,
    private val countryPrefs: CountryPrefs,
) : CountryRepository {

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private var isLocalStorageEnabled: Boolean = false
    private var isFavoritesFeatureEnabled: Boolean = false

    private val _countries: MutableStateFlow<List<Country>> = MutableStateFlow(emptyList())
    override val countries: StateFlow<List<Country>> = _countries.asStateFlow()

    init {
        coroutineScope.launch {
            isLocalStorageEnabled = countryPrefs.getLocalStorageEnabled().first()
            isFavoritesFeatureEnabled = countryPrefs.getFavoritesFeatureEnabled().first()
        }

        coroutineScope.launch {
            countryPrefs.getLocalStorageEnabled()
                .collect { localStorageEnabled ->
                    isLocalStorageEnabled = localStorageEnabled
                }
        }

        coroutineScope.launch {
            countryPrefs.getFavoritesFeatureEnabled()
                .collect { favoritesFeatureEnabled ->
                    isFavoritesFeatureEnabled = favoritesFeatureEnabled
                }
        }
    }

    override suspend fun fetchCountries() {
        val favorites = if (isFavoritesFeatureEnabled) {
            if (isLocalStorageEnabled) {
                countryDao.getFavoriteCountries()
            } else {
                _countries.value.filter { it.isFavorite }
            }
        } else {
            emptyList()
        }

        try {
            val countriesResponse = service.getAllCountries()

            if (isLocalStorageEnabled) countryDao.deleteAllCountries()

            _countries.value = emptyList()
            _countries.value = if (countriesResponse.isSuccessful) {
                val countries = countriesResponse.body()!!
                    .toMutableList()
                    .map { country ->
                        country.copy(isFavorite = favorites.any { it.commonName == country.commonName })
                    }
                if (isLocalStorageEnabled) countryDao.addCountries(*countries.toTypedArray())
                countries
            } else {
                throw Exception("Request failed: ${countriesResponse.errorBody()}")
            }
        } catch (e: Exception) {
            if (isLocalStorageEnabled) {
                _countries.value = emptyList()
                _countries.value = countryDao.getAllCountries()
            } else {
                throw Exception("Request failed: ${e.message}")
            }
        }
    }

    override fun getCountry(index: Int): Country? =
        _countries.value.getOrNull(index)

    override suspend fun favorite(country: Country) {
        if (isFavoritesFeatureEnabled.not()) return

        val index = _countries.value.indexOf(country)
        val mutableCountries = _countries.value.toMutableList()
        val updatedCountry = country.copy(isFavorite = country.isFavorite.not())
        mutableCountries[index] = updatedCountry

        if (isLocalStorageEnabled) countryDao.updateCountry(updatedCountry)

        _countries.value = mutableCountries.toList()
    }
}