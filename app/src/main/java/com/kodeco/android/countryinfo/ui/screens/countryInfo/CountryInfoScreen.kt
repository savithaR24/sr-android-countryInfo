package com.kodeco.android.countryinfo.ui.screens.countryInfo

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.models.CountryFlags
import com.kodeco.android.countryinfo.models.CountryName
import com.kodeco.android.countryinfo.ui.components.CountryErrorScreen
import com.kodeco.android.countryinfo.ui.components.CountryInfoList
import com.kodeco.android.countryinfo.ui.components.Loading
import kotlinx.coroutines.DelicateCoroutinesApi

sealed class CountryInfoState {
    data object Loading : CountryInfoState()
    data class Success(val countries: List<Country>) : CountryInfoState()
    data class Error(val error: Throwable) : CountryInfoState()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun CountryInfoScreen(
    viewModel: CountryInfoViewModel,
    onCountryRowTap: (Int) -> Unit,
    onAboutTap: () -> Unit,
) {

    var infoState = viewModel.uiState.collectAsState()
//    val appUptimeCounter = viewModel.appUptimeCounter.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Country Info")
                },
                actions = {
                    IconButton(
                        onClick = onAboutTap,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Help,
                            contentDescription = "About",
                        )
                    }
                }
            )
        }
    ) { padding ->
        when (val currentState = infoState.value) {
            is CountryInfoState.Loading -> Loading()

            is CountryInfoState.Success -> CountryInfoList(
                modifier = Modifier.padding(padding),
                currentState.countries,
                onRefreshPress = {
                    viewModel.fetchCountries()
                },
                onCountryTap = {
                    onCountryRowTap(it)
                },
            )

            is CountryInfoState.Error -> CountryErrorScreen(
                currentState.error,
                onTryAgain = {
                    viewModel.fetchCountries()
                })
        }
    }

    /*
    Surface {
        when (val currentState = infoState.value) {
            is CountryInfoState.Loading -> Loading(
                appUptimeCounter = appUptimeCounter.value,
            )

            is CountryInfoState.Success -> CountryInfoList(
                currentState.countries,
                onRefreshPress = {
                    viewModel.fetchCountries()
                },
                onCountryTap = {
                    onCountryRowTap(it)
                },
                onBackTap = {
                },
            )

            is CountryInfoState.Error -> CountryErrorScreen(
                currentState.error,
                onTryAgain = {
                    viewModel.fetchCountries()
                })
        }
    }
    */

}

val sampleListCountries = listOf(
    Country(
        name = CountryName(common = "United States"),
        capital = listOf("D.C"),
        population = 336102425,
        area = 9833520.0,
        flags = CountryFlags(png = "https://flagcdn.com/w320/us.png"),
    ),
    Country(
        name = CountryName(common = "United States"),
        capital = listOf("D.C"),
        population = 336102425,
        area = 9833520.0,
        flags = CountryFlags(png = "https://flagcdn.com/w320/us.png"),
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
