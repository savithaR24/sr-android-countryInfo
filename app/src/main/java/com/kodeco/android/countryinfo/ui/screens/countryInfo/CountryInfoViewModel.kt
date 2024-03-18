package com.kodeco.android.countryinfo.ui.screens.countryInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.repositories.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CountryInfoViewModel(private val repository: CountryRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<CountryInfoState> =
        MutableStateFlow(CountryInfoState.Loading)
    val uiState: StateFlow<CountryInfoState> = _uiState.asStateFlow()

    init {
        fetchCountries()
    }

    class CountryInfoViewModelFactory(private val repository: CountryRepository) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            CountryInfoViewModel(repository) as T
    }

    fun fetchCountries() {
        viewModelScope.launch {
            _uiState.value = CountryInfoState.Loading
            repository.fetchCountries()
                .catch {
                    _uiState.value = CountryInfoState.Error(it)
                }
                .collect {
                    _uiState.value = CountryInfoState.Success(it)
                }
        }
    }
}





