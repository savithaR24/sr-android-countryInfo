package com.kodeco.android.countryinfo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.models.Country
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.flow.Flows

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryInfoList(countries: List<Country>, onRefreshPress: () -> Unit) {
    var selectedCountry: Country? by remember { mutableStateOf(null) }

    val tapCounter = Flows.tapFlow.collectAsState()
    val backCounter = Flows.backFlow.collectAsState()

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
            Text(text = "Taps: ${tapCounter.value}", modifier = Modifier.padding(10.dp))

            Button(onClick = {
                onRefreshPress()
            }) {
                Text(text = "Refresh")
            }

            Text(text = "Back: ${backCounter.value}", modifier = Modifier.padding(10.dp))
        }

            selectedCountry?.let {
                CountryDetailsScreen(it, onBackPress = {
                    selectedCountry = null
                })
            } ?: run {
                LazyColumn {
                    items(countries) {
                        CountryInfoRow(it) {
                            selectedCountry = it
                            Flows.tap()
                        }
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun CountryInfoListPreview() {
        CountryInfoList(sampleListCountries, onRefreshPress = {})
    }
