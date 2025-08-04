package com.paradise.feature.circuit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.paradise.core.model.Category
import com.paradise.core.ui.route.Route
import com.paradise.feature.circuit.CircuitRoute
import com.paradise.feature.circuit.screen.add.CircuitAddScreen
import com.paradise.feature.circuit.screen.category.CircuitCategoryScreen

fun NavGraphBuilder.circuitScreen(
    onBackClick: () -> Unit,
    onCircuitCategoryClick: (Category) -> Unit,
    onCircuitAddClick: () -> Unit,
    onMovementCountSelected: (Int) -> Unit,
) {
    navigation<Route.CircuitBase>(startDestination = Route.CircuitRoute) {
        composable<Route.CircuitRoute> {
            CircuitRoute(
                onBackClick = onBackClick,
                onCategoryClick = onCircuitCategoryClick,
            )
        }

        composable<Route.CircuitCategory> { backStackEntry ->
            val args: Route.CircuitCategory = backStackEntry.toRoute()
            val category = args.category

            CircuitCategoryScreen(
                category = category,
                onBackClick = onBackClick,
                onAddClick = onCircuitAddClick,
            )
        }

        composable<Route.CircuitAdd> {
            CircuitAddScreen(
                onBackClick = onBackClick,
                onMovementCountSelected = onMovementCountSelected,
            )
        }
    }
}
