package com.paradise.ptpt.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.paradise.core.ui.route.Route
import com.paradise.ptpt.navigation.BottomNavDestination

@Composable
fun rememberAppNavState(navController: NavHostController = rememberNavController()): AppNavState {
    return remember(navController) {
        AppNavState(
            navController = navController,
        )
    }
}

@Stable
class AppNavState(
    val navController: NavHostController,
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)

    val currentDestination: NavDestination?
        @Composable get() {
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)

            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    val currentBottomNavDestination: BottomNavDestination?
        @Composable get() {
            return BottomNavDestination.entries.firstOrNull { destination ->
                currentDestination?.hasRoute(route = destination.route) == true
            }
        }

    fun navigateToDestination(bottomNavDestination: BottomNavDestination) {
        val options = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        navController.navigateRoute(bottomNavDestination, options)
    }

    private fun NavController.navigateRoute(
        bottomNavDestination: BottomNavDestination,
        options: NavOptions? = null,
    ) {
        when (bottomNavDestination) {
            BottomNavDestination.HOME -> navigate(Route.Home, options)
            BottomNavDestination.RECORD -> navigate(Route.Record, options)
            BottomNavDestination.MY -> navigate(Route.My, options)
        }
    }
}
