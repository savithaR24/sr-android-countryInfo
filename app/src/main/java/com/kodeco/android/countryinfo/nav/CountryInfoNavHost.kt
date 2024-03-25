package com.kodeco.android.countryinfo.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.ui.screens.about.AboutScreen
import com.kodeco.android.countryinfo.ui.screens.countryDetails.CountryDetailsScreen
import com.kodeco.android.countryinfo.ui.screens.countryDetails.CountryDetailsViewModel
import com.kodeco.android.countryinfo.ui.screens.countryInfo.CountryInfoScreen
import com.kodeco.android.countryinfo.ui.screens.countryInfo.CountryInfoViewModel

@Composable
fun CountryInfoNavHost(repository: CountryRepository) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationItem.List.route
    ) {
        composable(
            NavigationItem.List.route
        ) {
            CountryInfoScreen(
                viewModel = viewModel(
                    factory = CountryInfoViewModel.CountryInfoViewModelFactory(
                        repository = repository,
                    ),
                ),
                onCountryRowTap = {
                    navController.navigate("${NavigationItem.Details.route}/$it")
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
                    viewModel = viewModel(
                        factory = CountryDetailsViewModel.CountryDetailsViewModelFactory(
                            countryId = id,
                            repository = repository,
                        ),
                    ),
                    onBackPress = {
                        navController.navigate(NavigationItem.List.route)
                    }
                )
            }
        }

        composable(
            NavigationItem.About.route
        ) {
            AboutScreen (
                onBackPress = {
                    navController.navigate(NavigationItem.List.route)
                }
            )
        }
    }
}
