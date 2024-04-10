package com.kodeco.android.countryinfo.ui.screens.countryInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.prefs.CountryPrefs
import com.kodeco.android.countryinfo.repositories.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryInfoViewModel @Inject constructor(
    private val repository: CountryRepository,
    private val prefs: CountryPrefs,
) : ViewModel() {
    private var isFavoritesFeatureEnabled = false
        set(value) {
            field = value
            val uiStateValue = _uiState.value
            if (uiStateValue is CountryInfoState.Success) {
                _uiState.value = uiStateValue.copy(isFavoritesFeatureEnabled = value)
            }
        }

    private val _uiState: MutableStateFlow<CountryInfoState> =
        MutableStateFlow(CountryInfoState.Loading)
    val uiState: StateFlow<CountryInfoState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            prefs.getFavoritesFeatureEnabled()
                .collect{
                    isFavoritesFeatureEnabled = it
                }
        }

        viewModelScope.launch {
            repository
                .countries
                .catch {
                    _uiState.value = CountryInfoState.Error(it)
                }
                .collect {
                    _uiState.value = CountryInfoState.Success(
                        it,
                        isFavoritesFeatureEnabled = isFavoritesFeatureEnabled,
                        )
                }
        }
        fetchCountries()
    }

    fun fetchCountries() {
        _uiState.value = CountryInfoState.Loading

        viewModelScope.launch {
            try {
                repository.fetchCountries()
            } catch (e: Exception) {
                _uiState.value = CountryInfoState.Error(e)
            }
        }
    }

    fun favorite(country: Country) {
        viewModelScope.launch {
            repository.favorite(country)
        }
    }
}





