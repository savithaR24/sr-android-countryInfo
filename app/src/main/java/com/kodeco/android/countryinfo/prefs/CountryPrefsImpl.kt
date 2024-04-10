package com.kodeco.android.countryinfo.prefs

import android.content.Context
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CountryPrefsImpl @Inject constructor(@ApplicationContext context: Context) : CountryPrefs {
    private val Context.dataStore by preferencesDataStore(name = "country_prefs")
    private val dataStore = context.dataStore
    override fun getLocalStorageEnabled(): Flow<Boolean> = dataStore.data
        .catch {
            emit(emptyPreferences())
        }
        .map {
            it[STORAGE_KEY_LOCAL_STORAGE] ?: false
        }

    override fun getFavoritesFeatureEnabled(): Flow<Boolean> = dataStore.data
        .catch {
            emit(emptyPreferences())
        }
        .map {
            it[STORAGE_KEY_FAVORITES_FEATURE] ?: false
        }

    override suspend fun toggleLocalStorage() {
        dataStore.edit {
            it[STORAGE_KEY_LOCAL_STORAGE] = it[STORAGE_KEY_LOCAL_STORAGE]?.not() ?: false
        }
    }

    override suspend fun toggleFavoritesFeature() {
        dataStore.edit {
            it[STORAGE_KEY_FAVORITES_FEATURE] = it[STORAGE_KEY_FAVORITES_FEATURE]?.not() ?: false
        }
    }

    companion object {
        private val STORAGE_KEY_LOCAL_STORAGE = booleanPreferencesKey("local_storage")
        private val STORAGE_KEY_FAVORITES_FEATURE = booleanPreferencesKey("favorites_feature")
    }
}
