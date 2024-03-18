package com.kodeco.android.countryinfo.ui.screens.countryInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.repositories.CountryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CountryInfoViewModel(private val repository: CountryRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<CountryInfoState> =
        MutableStateFlow(CountryInfoState.Loading)
    val uiState: StateFlow<CountryInfoState> = _uiState.asStateFlow()

    private val _countryTapCounter: MutableStateFlow<Int> = MutableStateFlow(0)
    val countryTapCounter: StateFlow<Int> = _countryTapCounter.asStateFlow()

    private val _backTapCounter: MutableStateFlow<Int> = MutableStateFlow(0)
    val backTapCounter: StateFlow<Int> = _backTapCounter.asStateFlow()

    private val _appUptimeCounter: MutableStateFlow<Int> = MutableStateFlow(0)
    val appUptimeCounter: StateFlow<Int> = _appUptimeCounter.asStateFlow()

    private val _refreshCounter: MutableStateFlow<Int> = MutableStateFlow(0)
    val refreshCounter: StateFlow<Int> = _refreshCounter.asStateFlow()

    init {
        incrementCounter()
        fetchCountries()
    }

    class CountryInfoViewModelFactory(private val repository: CountryRepository) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            CountryInfoViewModel(repository) as T
    }

    private fun incrementCounter() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                increment()
            }
        }
    }

    private fun increment() {
        _appUptimeCounter.value += 1
    }

    fun tap() {
        _countryTapCounter.value += 1
    }

    fun tapBack() {
        _backTapCounter.value += 1
    }
    fun refresh() {
        _refreshCounter.value += 1
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





