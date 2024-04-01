package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.models.CountryFlags
import com.kodeco.android.countryinfo.models.CountryName

@Composable
fun CountryInfoRow(
    country: Country,
    onClick: () -> Unit,
    onFavorite: () -> Unit,
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "Name: ${country.commonName}")
                Text(text = "Capital: ${country.nonNullCapital}")
            }
            CountryFavoriteStar(country = country, onTap = onFavorite)
        }
    }
}

val sampleCountry = Country(
    name = CountryName(common = "United States"),
    capital = listOf("D.C"),
    population = 336102425,
    area = 9833520.0,
    flags = CountryFlags(png = "https://flagcdn.com/w320/us.png"),
)

@Preview
@Composable
fun CountryInfoRowPreview() {
//    CountryInfoRow(
//        country = sampleCountry,
//        onClick = {}
//    )
}
