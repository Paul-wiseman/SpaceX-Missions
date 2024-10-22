package com.mindera.rocketscience.core_ui.ui.navigation

import kotlinx.serialization.Serializable

sealed class Route {
    @Serializable
    data object Home : Route()

    @Serializable
    data class Details(
        val wikipediaLink: String?,
        val articleLink: String?
    ) : Route()
}