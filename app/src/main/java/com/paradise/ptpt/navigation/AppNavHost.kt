package com.paradise.ptpt.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.createGraph
import com.paradise.core.ui.route.Route
import com.paradise.feature.auth.navigation.authScreen
import com.paradise.feature.home.navigation.homeScreen
import com.paradise.feature.matching.navigation.matchingScreen
import com.paradise.feature.my.navigation.myScreen
import com.paradise.feature.record.navigation.recordScreen
import com.paradise.feature.tracking.navigation.trackingScreen
import com.paradise.ptpt.contract.AppState
import com.paradise.ptpt.ui.AppNavState

@Composable
fun AppNavHost(
    appNavState: AppNavState,
    authState: AppState.AuthState,
    modifier: Modifier = Modifier,
) {
    val navController = appNavState.navController

    val startDest = remember(authState) {
        if (authState == AppState.AuthState.Unauthenticated) Route.Auth else Route.Home
    }

    val navGraph = remember(startDest) {
        navController.createGraph(startDestination = startDest) {
            authScreen()
            homeScreen()
            matchingScreen()
            trackingScreen()
            myScreen()
            recordScreen()
        }
    }

    LaunchedEffect(navGraph) {
        navController.graph = navGraph
    }

    NavHost(
        navController = navController,
        graph = navGraph,
        modifier = modifier,
    )
}
