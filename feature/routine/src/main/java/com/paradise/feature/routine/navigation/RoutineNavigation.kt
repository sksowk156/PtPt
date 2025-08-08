package com.paradise.feature.routine.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.paradise.core.model.Category
import com.paradise.core.ui.route.Route
import com.paradise.feature.routine.RoutineRoute
import com.paradise.feature.routine.screen.add.analyze.RoutineAnalyzeScreen
import com.paradise.feature.routine.screen.add.save.RoutineSaveScreen
import com.paradise.feature.routine.screen.add.select.RoutineSelectScreen
import com.paradise.feature.routine.screen.category.RoutineCategoryScreen

fun NavGraphBuilder.routineScreen(
    onBackClick: () -> Unit,
    onRoutineCategoryClick: (Category) -> Unit,
    onRoutineAddClick: () -> Unit,
    onRoutineAddCloseClick: () -> Unit,
    onPoseCountSelected: (Int) -> Unit,
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

        composable<Route.RoutineBase.RoutineAdd.Select> {
            RoutineSelectScreen(
                onCloseClick = onRoutineAddCloseClick,
                onBackClick = onBackClick,
                onPoseCountSelected = onPoseCountSelected,
            )
        }

        composable<Route.RoutineBase.RoutineAdd.Analyze> {
            RoutineAnalyzeScreen(
                onCloseClick = onRoutineAddCloseClick,
                onBackClick = onBackClick,
            )
        }

        composable<Route.RoutineBase.RoutineAdd.Save> {
            RoutineSaveScreen(
                onCloseClick = onRoutineAddCloseClick,
                onBackClick = onBackClick,
            )
        }
    }
}
