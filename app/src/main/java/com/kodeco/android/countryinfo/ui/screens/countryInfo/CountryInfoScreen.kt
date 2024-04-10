package com.kodeco.android.countryinfo.ui.screens.countryInfo

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.ui.components.CountryErrorScreen
import com.kodeco.android.countryinfo.ui.components.CountryInfoList
import com.kodeco.android.countryinfo.ui.components.Loading

sealed class CountryInfoState {
    data object Loading : CountryInfoState()
    data class Success(
        val countries: List<Country>,
        val isFavoritesFeatureEnabled: Boolean,
    ) : CountryInfoState()

    data class Error(val error: Throwable) : CountryInfoState()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CountryInfoScreen(
    viewModel: CountryInfoViewModel,
    onCountryRowTap: (Int) -> Unit,
    onAboutTap: () -> Unit,
    onSettingsTap: () -> Unit,
) {

    val infoState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Country Info")
                },
                actions = {
                    IconButton(
                        onClick = onSettingsTap,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings",
                        )
                    }

                    IconButton(
                        onClick = onAboutTap,
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Help,
                            contentDescription = "About",
                        )
                    }
                }
            )
        }
    ) { padding ->
        val transition = updateTransition(
            targetState = infoState,
            label = "list_state_transition",
        )
        transition.Crossfade(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentKey = { it.javaClass },
        ) { state ->
            when (state) {
                is CountryInfoState.Loading -> Loading()

                is CountryInfoState.Success -> CountryInfoList(
                    state,
                    onRefreshPress = {
                        viewModel.fetchCountries()
                    },
                    onCountryTap = {
                        onCountryRowTap(it)
                    },
                    onCountryFavorite = {
                        viewModel.favorite(it)
                    }
                )

                is CountryInfoState.Error -> CountryErrorScreen(
                    state.error,
                    onTryAgain = {
                        viewModel.fetchCountries()
                    })
            }
        }
    }
}

val sampleListCountries = listOf(
    Country(
        commonName = "United States",
        mainCapital = "D.C",
        population = 336102425,
        area = 9833520.0,
        flagUrl = "https://flagcdn.com/w320/us.png",
        isFavorite = false,
    ),
    Country(
        commonName = "United States",
        mainCapital = "D.C",
        population = 336102425,
        area = 9833520.0,
        flagUrl = "https://flagcdn.com/w320/us.png",
        isFavorite = false,
    ),
)

@Preview
@Composable
fun CountryInfoScreenPreview() {
//    CountryInfoScreen(object : CountryAPIService {
//        override suspend fun getAllCountries(): Response<List<Country>> =
//            Response.success(sampleListCountries)
//    })
}
