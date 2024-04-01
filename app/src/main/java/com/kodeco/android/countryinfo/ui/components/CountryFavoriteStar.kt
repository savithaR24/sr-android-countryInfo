package com.kodeco.android.countryinfo.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.ui.theme.CoolBlue
import com.kodeco.android.countryinfo.ui.theme.DarkBlue

@Composable
fun CountryFavoriteStar(
    country: Country,
    onTap: () -> Unit
) {
    val favoriteTransition = updateTransition(
        targetState = country.isFavorite,
        label = "${country.commonName}_favorite_transition",
    )
    val scaleAnimation by favoriteTransition.animateFloat(
        transitionSpec = {
            if (!country.isFavorite) tween(0) else {
                keyframes {
                    durationMillis = 1000
                    1.0f at 0 with FastOutSlowInEasing
                    0.75f at 400 with FastOutSlowInEasing
                    1.5f at 700 with FastOutSlowInEasing
                    1.0f at 1000 with FastOutSlowInEasing
                }
            }
        },
        label = "${country.commonName}_favorite_size",
    ) { state ->
        if (state) {
            1.5f
        } else {
            1.0f
        }
    }
    val rotationAnimation by favoriteTransition.animateFloat(
        transitionSpec = { tween(if (!country.isFavorite) 0 else 750) },
        label = "${country.commonName}_favorite_rotation",
    ) { state ->
        if (state) {
            360.0f
        } else {
            0.0f
        }
    }
    val colorAnimation by favoriteTransition.animateColor(
        transitionSpec = {
            if (!country.isFavorite) tween(0) else {
                keyframes {
                    durationMillis = 1000
                    CoolBlue at 700 with FastOutSlowInEasing
                    DarkBlue at 1000 with FastOutSlowInEasing
                }
            }
        },
        label = "${country.commonName}_favorite_color",
    ) { state ->
        if (state) {
            DarkBlue
        } else {
            LocalContentColor.current
        }
    }
    Crossfade(
        targetState = country.isFavorite,
        animationSpec = tween(500),
        modifier = Modifier.padding(all = 8.dp),
        label = "${country.commonName}_favorite_crossfade",
    ) { state ->
        IconButton(onClick = onTap) {
            Icon(
                painter = painterResource(
                    id = if (state) {
                        R.drawable.star_filled
                    } else {
                        R.drawable.star_outline
                    },
                ),
                contentDescription = "favorite",
                modifier = Modifier
                    .padding(all = 8.dp)
                    .size(32.dp)
                    .graphicsLayer(
                        scaleX = scaleAnimation,
                        scaleY = scaleAnimation,
                        rotationZ = rotationAnimation,
                    ),
                tint = colorAnimation,
            )
        }
    }

}