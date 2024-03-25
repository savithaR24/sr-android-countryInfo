package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.models.Country

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryInfoList(
    modifier: Modifier,
    countries: List<Country>,
    onRefreshPress: () -> Unit,
    onCountryTap: (Int) -> Unit,
) {

    var selectedCountry: Country? by remember { mutableStateOf(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                onRefreshPress()
            }) {
                Text(text = "Refresh")
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            items(countries) {
                CountryInfoRow(it) {
                    selectedCountry = it
                    onCountryTap(countries.indexOf(it))
                }
            }
        }
    }
}

@Preview
@Composable
fun CountryInfoListPreview() {
//        CountryInfoList(sampleListCountries, onRefreshPress = {})
}
