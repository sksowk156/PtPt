package com.paradise.core.ui.route

import com.paradise.core.model.Category
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
    data object CircuitBase : Route

    @Serializable
    data object CircuitRoute : Route

    @Serializable
    data object CircuitAdd : Route

    @Serializable
    data class CircuitCategory(val category: Category) : Route

    @Serializable
    data object My : Route

    @Serializable
    data object Record : Route

    @Serializable
    data object Tracking : Route
}
