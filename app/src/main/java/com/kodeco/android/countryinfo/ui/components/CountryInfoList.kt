package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.ui.screens.countryDetails.CountryDetailsScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryInfoList(
    countries: List<Country>,
    onRefreshPress: () -> Unit,
    onCountryTap: () -> Unit,
    onBackTap: () -> Unit,
    tapCounter: Int,
    backCounter: Int,
) {
    var selectedCountry: Country? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Taps: $tapCounter", modifier = Modifier.padding(10.dp))

            Button(onClick = {
                onRefreshPress()
            }) {
                Text(text = "Refresh")
            }

            Text(text = "Back: $backCounter", modifier = Modifier.padding(10.dp))
        }

        selectedCountry?.let {
            CountryDetailsScreen(it, onBackPress = {
                onBackTap()
                selectedCountry = null
            })
        } ?: run {
            LazyColumn {
                items(countries) {
                    CountryInfoRow(it) {
                        selectedCountry = it
                        onCountryTap()
                    }
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
