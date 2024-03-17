package com.kodeco.android.countryinfo.ui.screens.countryDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kodeco.android.countryinfo.flow.Flows
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.ui.components.sampleCountry

@Composable
fun CountryDetailsScreen(country: Country, onBackPress: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(country.commonName)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        Flows.tapBack()
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
                    modifier = Modifier
                        .size(200.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun CountryDetailsScreenPreview() {
    CountryDetailsScreen(
        country = sampleCountry,
        onBackPress = {}
    )
}
