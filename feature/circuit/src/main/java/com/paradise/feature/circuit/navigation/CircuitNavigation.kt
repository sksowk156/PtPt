package com.paradise.feature.circuit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.paradise.core.ui.route.Route
import com.paradise.feature.circuit.CircuitRoute

fun NavGraphBuilder.circuitScreen() {
    composable<Route.Circuit> {
        CircuitRoute()
    }
}
