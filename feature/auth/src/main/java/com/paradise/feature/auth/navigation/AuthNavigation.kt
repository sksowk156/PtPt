package com.paradise.feature.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.paradise.core.ui.route.Route
import com.paradise.feature.auth.AuthRoute

fun NavGraphBuilder.authScreen() {
    composable<Route.Auth> {
        AuthRoute()
    }
}
