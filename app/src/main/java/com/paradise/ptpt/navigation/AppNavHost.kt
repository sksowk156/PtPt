package com.paradise.ptpt.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.paradise.core.ui.route.Route
import com.paradise.feature.auth.navigation.authScreen
import com.paradise.feature.home.navigation.homeScreen
import com.paradise.feature.matching.navigation.matchingScreen
import com.paradise.feature.my.navigation.myScreen
import com.paradise.feature.record.navigation.recordScreen
import com.paradise.feature.tracking.navigation.trackingScreen
import com.paradise.ptpt.ui.AppNavState

@Composable
fun AppNavHost(
    appNavState: AppNavState,
    modifier: Modifier = Modifier,
) {
    val navController = appNavState.navController
    NavHost(
        navController = navController,
        startDestination = Route.Home,
        modifier = modifier,
    ) {
        authScreen()

        homeScreen()

        matchingScreen()

        trackingScreen()

        myScreen()

        recordScreen()
    }
}
