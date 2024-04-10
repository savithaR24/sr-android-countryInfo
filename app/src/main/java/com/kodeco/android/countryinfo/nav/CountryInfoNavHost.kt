package com.kodeco.android.countryinfo.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kodeco.android.countryinfo.ui.screens.about.AboutScreen
import com.kodeco.android.countryinfo.ui.screens.countryDetails.CountryDetailsScreen
import com.kodeco.android.countryinfo.ui.screens.countryInfo.CountryInfoScreen
import com.kodeco.android.countryinfo.ui.screens.settings.SettingsScreen

@Composable
fun CountryInfoNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationItem.List.route
    ) {
        composable(
            NavigationItem.List.route
        ) {
            CountryInfoScreen(
                viewModel = hiltViewModel(),
                onCountryRowTap = {
                    navController.navigate("${NavigationItem.Details.route}/$it")
                },
                onSettingsTap = {
                    navController.navigate(NavigationItem.Settings.route)
                },
                onAboutTap = {
                    navController.navigate(NavigationItem.About.route)
                }
            )
        }

        composable(
            "${NavigationItem.Details.route}/{countryId}",
            arguments = listOf(navArgument("countryId") { type = NavType.IntType }),
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("countryId")
            id?.let {
                CountryDetailsScreen(
                    countryId = id,
                    viewModel = hiltViewModel(),
                    onBackPress = {
                        navController.navigate(NavigationItem.List.route)
                    }
                )
            }
        }

        composable(
            NavigationItem.About.route
        ) {
            AboutScreen(
                onBackPress = {
                    navController.navigate(NavigationItem.List.route)
                }
            )
        }

        composable(
            NavigationItem.Settings.route
        ) {
            SettingsScreen(
                onBackPress = {
                    navController.navigate(NavigationItem.List.route)
                }
            )
        }
    }
}
