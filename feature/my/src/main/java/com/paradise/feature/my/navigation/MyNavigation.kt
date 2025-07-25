package com.paradise.feature.my.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.paradise.core.ui.route.Route
import com.paradise.feature.my.MyRoute

fun NavGraphBuilder.myScreen() {
    composable<Route.My> {
        MyRoute()
    }
}
