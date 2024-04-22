package com.kodeco.android.countryinfo.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.prefs.CountryPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val prefs: CountryPrefs,
) : ViewModel() {

    private val _uiState: MutableStateFlow<SettingsUIState> =
        MutableStateFlow(SettingsUIState())
    val uiState: StateFlow<SettingsUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            prefs.getLocalStorageEnabled()
                .collect {
                    _uiState.value = _uiState.value.copy(localStorageEnabled = it)
                }
        }

        viewModelScope.launch {
            prefs.getFavoritesFeatureEnabled()
                .collect {
                    _uiState.value = _uiState.value.copy(favoritesFeatureEnabled = it)
                }
        }
    }

    fun toggleLocalStorage() {
        viewModelScope.launch {
            prefs.toggleLocalStorage()
        }
    }

    fun toggleFavoritesFeature() {
        viewModelScope.launch {
            prefs.toggleFavoritesFeature()
        }
    }
}
