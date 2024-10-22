package com.mindera.rocketscience.spacex_domain.model

import com.mindera.rocketscience.util.UiText

data class SpaceXLaunchItem(
    val id: Int,
    val missionName: String,
    val date: UiText,
    val year:String,
    val time:UiText,
    val rocket: String,
    val imageLink: String?,
    val success: Boolean = false,
    val daysSinceLaunch: Long = 0,
    val wikipediaLink: String?,
    val articleLink: String?
)
