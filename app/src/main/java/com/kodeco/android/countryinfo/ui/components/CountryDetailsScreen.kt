package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kodeco.android.countryinfo.models.Country

@Composable
fun CountryDetailsScreen(country: Country) {
    LazyColumn {
        item { Text(text = "Capital: ${country.nonNullCapital}") }
        item { Text(text = "Population: ${country.population}") }
        item { Text(text = "Area: ${country.area}") }
        item {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(country.flagUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Flag",
                contentScale = ContentScale.Fit,
            )
        }
    }
}

@Preview
@Composable
fun CountryDetailsScreenPreview() {
    CountryDetailsScreen(country = sampleCountry)
}
