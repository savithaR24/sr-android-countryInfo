package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.models.Country
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun CountryInfoList(countries: List<Country>) {
    var selectedCountry: Country? by remember { mutableStateOf(null) }

    selectedCountry?.let {
        CountryDetailsScreen(it, onBackPress = {
            selectedCountry = null
        })
    } ?: run {
        Scaffold (
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Countries of the World")
                    }
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
            ) {
                items(countries) {
                    CountryInfoRow(it) {
                        selectedCountry = it
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CountryInfoListPreview() {
    CountryInfoList(sampleListCountries)
}
