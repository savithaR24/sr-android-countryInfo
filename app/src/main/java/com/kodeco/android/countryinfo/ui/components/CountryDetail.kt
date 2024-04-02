package com.kodeco.android.countryinfo.ui.components

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kodeco.android.countryinfo.models.Country

@Composable
fun CountryDetail(
    modifier: Modifier,
    country: Country) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item { Text(text = "Capital: ${country.nonNullCapital}") }
        item { Text(text = "Population: ${country.population}") }
        item { Text(text = "Area: ${country.area}") }
        item {
            var expanded by remember { mutableStateOf(false) }
            val flagTransition = updateTransition(
                targetState = expanded,
                label = "${country.commonName}_details_transition",
            )
            val flagSize by flagTransition.animateDp(
                label = "${country.commonName}_details_size",
            ) { state ->
                if (state) {
                    300.dp
                } else {
                    150.dp
                }
            }
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(country.flagUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Flag",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(flagSize)
                    .clickable { expanded = !expanded },
            )
        }
    }
}