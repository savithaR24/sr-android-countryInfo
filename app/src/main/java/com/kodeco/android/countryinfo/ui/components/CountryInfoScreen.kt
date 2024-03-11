package com.kodeco.android.countryinfo.ui.components

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.api.CountryAPIService
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.models.CountryFlags
import com.kodeco.android.countryinfo.models.CountryName
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

sealed class CountryInfoState {
    data object Loading : CountryInfoState()
    data class Success(val countries: List<Country>) : CountryInfoState()
    data class Error(val error: Throwable) : CountryInfoState()
}

@Composable
fun CountryInfoScreen(service: CountryAPIService) {

    var infoState: CountryInfoState by remember { mutableStateOf(CountryInfoState.Loading) }
    Surface {
        when (val currentState = infoState) {
            is CountryInfoState.Loading -> Loading()
            is CountryInfoState.Success -> CountryInfoList(
                currentState.countries,
                onRefreshPress = {
                    infoState = CountryInfoState.Loading
                })

            is CountryInfoState.Error -> CountryErrorScreen(currentState.error, onTryAgain = {
                infoState = CountryInfoState.Loading
            })
        }
    }

    if (infoState == CountryInfoState.Loading) {
        LaunchedEffect(Unit) {
            getCountryInfoFlow(service)
                .catch {
                    infoState = CountryInfoState.Error(it)
                }
                .collect {
                    infoState = it
                }
        }
    }
}

private fun getCountryInfoFlow(service: CountryAPIService): Flow<CountryInfoState> = flow {
    delay(1500)
    val countriesResponse = service.getAllCountries()

    val newState = if (countriesResponse.isSuccessful) {
        CountryInfoState.Success(countriesResponse.body()!!)
    } else {
        CountryInfoState.Error(Throwable("Request failed: ${countriesResponse.message()}"))
    }

    emit(newState)
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
    CountryInfoScreen(object : CountryAPIService {
        override suspend fun getAllCountries(): Response<List<Country>> =
            Response.success(sampleListCountries)
    })
}
