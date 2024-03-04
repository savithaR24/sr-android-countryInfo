package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.models.Country
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun CountryInfoList(countries: List<Country>) {
    var selectedCountry: Country? by remember { mutableStateOf(null) }

    if (selectedCountry != null) {
        CountryDetailsScreen(selectedCountry!!)
    } else {
        LazyColumn {
            items(countries) {
                CountryInfoRow(it) {
                    selectedCountry = it
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
