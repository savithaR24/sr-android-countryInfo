package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.flow.Flows

@Composable
fun Loading() {
    val appUptimeCounter = Flows.counterFlow.collectAsState()
    val refreshCounter = Flows.refreshFlow.collectAsState()
    val combinedFlowValue = remember { mutableIntStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Loading .. App Uptime: ${appUptimeCounter.value}", Modifier.padding(10.dp))
            Text(text = "App Refreshed Count: ${refreshCounter.value}", Modifier.padding(10.dp))
            Text(text = "Total UI Interactions: ${combinedFlowValue.value}", Modifier.padding(10.dp))
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(Unit) {
        Flows.combinedFlow.collect {
            combinedFlowValue.value = it
        }
    }
}

@Preview
@Composable
fun LoadingPreview() {
    Loading()
}
