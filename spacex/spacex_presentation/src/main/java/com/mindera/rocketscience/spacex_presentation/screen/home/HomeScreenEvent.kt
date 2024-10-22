package com.mindera.rocketscience.spacex_presentation.screen.home

sealed class HomeScreenEvent {
    data class FilterSpaceXItems(
        val year: Int?,
        val onlySuccessfulLaunch: Boolean,
    ) : HomeScreenEvent()
}