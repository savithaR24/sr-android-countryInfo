package com.kodeco.android.countryinfo.ui.screens.countryDetails

import android.annotation.SuppressLint
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.ui.components.CountryDetail
import com.kodeco.android.countryinfo.ui.components.CountryErrorScreen
import com.kodeco.android.countryinfo.ui.components.Loading

sealed class CountryDetailsState {
    data object Loading : CountryDetailsState()
    data class Success(val country: Country) : CountryDetailsState()
    data class Error(val error: Throwable) : CountryDetailsState()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CountryDetailsScreen(
    viewModel: CountryDetailsViewModel,
    onBackPress: () -> Unit,
) {

    var detailsState = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = (detailsState.value as? CountryDetailsState.Success)?.country?.commonName.orEmpty())
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackPress()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        when (val currentState = detailsState.value) {
            is CountryDetailsState.Loading -> Loading()
            is CountryDetailsState.Success -> CountryDetail(currentState.country)
            is CountryDetailsState.Error -> CountryErrorScreen(
                currentState.error,
                onTryAgain = {
                    viewModel.getCountry()
                })
        }
    }
}

@Preview
@Composable
fun CountryDetailsScreenPreview() {
//    CountryDetailsScreen(
//        country = sampleCountry,
//        onBackPress = {}
//    )
}
