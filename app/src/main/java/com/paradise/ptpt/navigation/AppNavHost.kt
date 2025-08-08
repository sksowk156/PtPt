package com.paradise.ptpt.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.createGraph
import com.paradise.core.ui.route.Route
import com.paradise.feature.auth.navigation.authScreen
import com.paradise.feature.home.navigation.homeScreen
import com.paradise.feature.my.navigation.myScreen
import com.paradise.feature.record.navigation.recordScreen
import com.paradise.feature.routine.navigation.routineScreen
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
        if (authState == AppState.AuthState.Unauthenticated) Route.Auth else Route.HomeBase
    }

    val navGraph = remember(startDest) {
        navController.createGraph(startDestination = startDest) {
            navigation<Route.HomeBase>(startDestination = Route.HomeBase.Home) {
                homeScreen(
                    onRoutineClick = {
                        appNavState.navigateToRoute(route = Route.RoutineBase)
                    },
                    onTrackingClick = {
                        appNavState.navigateToRoute(route = Route.Tracking)
                    },
                )
                routineScreen(
                    onBackClick = {
                        appNavState.navigateUp()
                    },
                    onRoutineAddClick = {
                        appNavState.navigateToRoute(route = Route.RoutineBase.RoutineAdd)
                    },
                    onRoutineCategoryClick = { category ->
                        appNavState.navigateToRoute(route = Route.RoutineBase.RoutineCategory(category))
                    },
                    onRoutineAddCloseClick = {
                        appNavState.popBackToRoute<Route.RoutineBase.RoutineCategory>()
                    },
                )
                trackingScreen()
            }

            authScreen()
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
