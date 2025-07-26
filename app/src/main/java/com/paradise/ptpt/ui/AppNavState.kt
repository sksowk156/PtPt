package com.paradise.ptpt.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.paradise.core.ui.route.Route
import com.paradise.ptpt.navigation.BottomNavDestination
import kotlin.reflect.KClass

@Composable
fun rememberAppNavState(navController: NavHostController = rememberNavController()): AppNavState {
    return remember(navController) {
        AppNavState(navController = navController)
    }
}

@Stable
class AppNavState(
    val navController: NavHostController,
) {
    private val hideBottomBarRoutes = setOf(
        Route.Auth::class,
    )

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

    val shouldShowBottomBar: Boolean
        @Composable
        get() = hideBottomBarRoutes.none { route ->
            currentDestination?.hasRoute(route) == true
        }

    val currentBottomNavDestination: BottomNavDestination?
        @Composable get() {
            return BottomNavDestination.entries.firstOrNull { destination ->
                currentDestination?.hasRoute(route = destination.route) == true
            }
        }

    // 하단 네비게이션 이동
    fun navigateToDestination(bottomNavDestination: BottomNavDestination) {
        val options = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (bottomNavDestination) {
            BottomNavDestination.HOME -> navController.navigate(Route.Home, options)
            BottomNavDestination.RECORD -> navController.navigate(Route.Record, options)
            BottomNavDestination.MY -> navController.navigate(Route.My, options)
        }
    }

    // 뒤로가기
    fun navigateUp(): Boolean {
        return navController.navigateUp()
    }

    // 특정 route로 pop back
    fun popBackTo(
        route: KClass<*>,
        inclusive: Boolean = false,
    ) {
        navController.popBackStack(
            route = route,
            inclusive = inclusive,
        )
    }
}
