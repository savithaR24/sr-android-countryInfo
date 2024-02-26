package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.models.Country
import androidx.compose.foundation.lazy.items

@Composable
fun CountryInfoList(countries: List<Country>) {
    LazyColumn {
        items(countries) {
            CountryInfoRow(it)
        }
    }
}

@Preview
@Composable
fun CountryInfoListPreview() {
    CountryInfoList(sampleListCountries)
}
