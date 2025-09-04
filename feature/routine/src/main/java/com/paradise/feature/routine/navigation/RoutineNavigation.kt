package com.paradise.feature.routine.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.paradise.core.model.Category
import com.paradise.core.ui.route.Route
import com.paradise.feature.routine.RoutineRoute
import com.paradise.feature.routine.content.add.RoutineAddScreen
import com.paradise.feature.routine.content.category.RoutineCategoryScreen

fun NavGraphBuilder.routineScreen(
    onBackClick: () -> Unit,
    onRoutineCategoryClick: (Category) -> Unit,
    onRoutineAddClick: () -> Unit,
    onRoutineAddCloseClick: () -> Unit,
) {
    navigation<Route.RoutineBase>(startDestination = Route.RoutineBase.RoutineRoute) {
        composable<Route.RoutineBase.RoutineRoute> {
            RoutineRoute(
                onBackClick = onBackClick,
                onCategoryClick = onRoutineCategoryClick,
            )
        }

        composable<Route.RoutineBase.RoutineCategory> { backStackEntry ->
            val args: Route.RoutineBase.RoutineCategory = backStackEntry.toRoute()
            val category = args.category

            RoutineCategoryScreen(
                category = category,
                onBackClick = onBackClick,
                onAddClick = onRoutineAddClick,
            )
        }

        composable<Route.RoutineBase.RoutineAdd> {
            RoutineAddScreen(
                onCloseClick = onRoutineAddCloseClick,
            )
        }
    }
}
