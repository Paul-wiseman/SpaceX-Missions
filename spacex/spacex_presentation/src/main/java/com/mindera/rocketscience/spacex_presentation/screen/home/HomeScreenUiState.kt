package com.mindera.rocketscience.spacex_presentation.screen.home

import com.mindera.rocketscience.util.UiText

data class HomeScreenUiState(
    val companyName: String = "",
    val founderName: String = "",
    val employees: Int = 0,
    val launchSites: Int = 0,
    val founded: Int = 0,
    val valuation: Long = 0L,
    val error: UiText? = null
)
