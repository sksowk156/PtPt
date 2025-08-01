package com.paradise.core.ui.route

import kotlinx.serialization.Serializable

sealed interface Route {
    val base: Route get() = this

    @Serializable
    data object Auth : Route

    @Serializable
    data object HomeBase : Route

    @Serializable
    data object Home : Route

    @Serializable
    data object Circuit : Route

    @Serializable
    data object My : Route

    @Serializable
    data object Record : Route

    @Serializable
    data object Tracking : Route
}
