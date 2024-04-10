package com.kodeco.android.countryinfo.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kodeco.android.countryinfo.BuildConfig
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.prefs.CountryPrefs
import com.kodeco.android.countryinfo.ui.components.SettingsToggleRow
import com.kodeco.android.countryinfo.ui.screens.countryDetails.CountryDetailsViewModel
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme

data class SettingsUIState(
    val localStorageEnabled: Boolean = false,
    val favoritesFeatureEnabled: Boolean = false,
)

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Settings")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackPress()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            SettingsToggleRow(
                label = "Enable Local Storage",
                isToggleChecked = uiState.localStorageEnabled,
                onToggleChanged = {
                    viewModel.toggleLocalStorage()
                },
            )

            SettingsToggleRow(
                label = "Enable Favorites Feature",
                isToggleChecked = uiState.favoritesFeatureEnabled,
                onToggleChanged = {
                    viewModel.toggleFavoritesFeature()
            },
            )
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    MyApplicationTheme {
        SettingsScreen { }
    }
}