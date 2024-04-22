package com.kodeco.android.countryinfo.nav

enum class AppNavigation {
    LIST,
    DETAILS,
    ABOUT,
    SETTINGS,
}

sealed class NavigationItem(val route: String) {
    data object List : NavigationItem(AppNavigation.LIST.name)
    data object Details : NavigationItem(AppNavigation.DETAILS.name)
    data object About : NavigationItem(AppNavigation.ABOUT.name)
    data object Settings : NavigationItem(AppNavigation.SETTINGS.name)
}