package com.paradise.feature.matching.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.paradise.core.ui.route.Route
import com.paradise.feature.matching.MatchingRoute

fun NavGraphBuilder.matchingScreen() {
    composable<Route.Matching> {
        MatchingRoute()
    }
}
