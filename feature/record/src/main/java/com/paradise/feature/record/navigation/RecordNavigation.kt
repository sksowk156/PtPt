package com.paradise.feature.record.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.paradise.core.ui.route.Route
import com.paradise.feature.record.RecordRoute

fun NavGraphBuilder.recordScreen() {
    composable<Route.Record> {
        RecordRoute()
    }
}
