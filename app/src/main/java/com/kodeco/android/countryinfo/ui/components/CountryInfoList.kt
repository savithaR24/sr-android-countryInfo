package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.ui.screens.countryInfo.CountryInfoState

@Composable
fun CountryInfoList(
    countryInfoState: CountryInfoState.Success,
    onRefreshPress: () -> Unit,
    onCountryTap: (index: Int) -> Unit,
    onCountryFavorite: (country: Country) -> Unit,
) {

    Column(
        modifier = Modifier
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
            itemsIndexed(countryInfoState.countries) { index, country ->
                CountryInfoRow(
                    country = country,
                    isFavoritesFeatureEnabled = countryInfoState.isFavoritesFeatureEnabled,
                    onClick = {
                        onCountryTap(index)
                    },
                    onFavorite = {
                        onCountryFavorite(country)
                    },
                )
            }
        }
    }
}

@Preview
@Composable
fun CountryInfoListPreview() {
//        CountryInfoList(sampleListCountries, onRefreshPress = {})
}
