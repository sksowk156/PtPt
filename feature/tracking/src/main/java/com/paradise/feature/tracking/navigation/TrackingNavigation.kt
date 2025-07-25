package com.paradise.feature.tracking.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.paradise.core.ui.route.Route
import com.paradise.feature.tracking.TrackingRoute

fun NavGraphBuilder.trackingScreen() {
    composable<Route.Tracking> {
        TrackingRoute()
    }
}
