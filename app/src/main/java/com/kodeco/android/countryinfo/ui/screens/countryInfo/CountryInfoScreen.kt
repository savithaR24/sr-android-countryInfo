package com.kodeco.android.countryinfo.ui.screens.countryInfo

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.flow.Flows
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

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun CountryInfoScreen(viewModel: CountryInfoViewModel) {

    var infoState = viewModel.uiState.collectAsState()

    Surface {
        when (val currentState = infoState.value) {
            is CountryInfoState.Loading -> Loading()
            is CountryInfoState.Success -> CountryInfoList(
                currentState.countries,
                onRefreshPress = {
                    viewModel.fetchCountries()
                    Flows.refresh()
                })

            is CountryInfoState.Error -> CountryErrorScreen(
                currentState.error,
                onTryAgain = {
                    viewModel.fetchCountries()
                })
        }
    }
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
