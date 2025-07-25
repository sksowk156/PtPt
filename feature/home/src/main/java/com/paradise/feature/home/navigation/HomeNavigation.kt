package com.paradise.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.paradise.core.ui.route.Route
import com.paradise.feature.home.HomeRoute

fun NavGraphBuilder.homeScreen() {
    composable<Route.Home> {
        HomeRoute()
    }
}
