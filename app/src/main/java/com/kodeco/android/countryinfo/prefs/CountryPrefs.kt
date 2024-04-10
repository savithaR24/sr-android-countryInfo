package com.kodeco.android.countryinfo.prefs

import kotlinx.coroutines.flow.Flow

interface CountryPrefs {
    fun getLocalStorageEnabled(): Flow<Boolean>
    fun getFavoritesFeatureEnabled(): Flow<Boolean>

    suspend fun toggleLocalStorage()
    suspend fun toggleFavoritesFeature()
}