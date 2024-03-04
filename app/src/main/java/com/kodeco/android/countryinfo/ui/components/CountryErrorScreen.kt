package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CountryErrorScreen(error: Throwable, onTryAgain: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var customError = error.message
        if (customError != null && customError.contains("Unable to resolve host")) {
            customError = "No Network Connection. Please Check Connection and Try Again!"
        }
        Text(text = "Error: $customError",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier
            .height(10.dp)
        )
        Button(onClick = onTryAgain) {
            Text(text = "Try Again")
        }
    }
}

@Preview
@Composable
fun CountryErrorScreenPreview() {
    CountryErrorScreen(
        error = Throwable("Error message"),
        onTryAgain = {}
    )
}
