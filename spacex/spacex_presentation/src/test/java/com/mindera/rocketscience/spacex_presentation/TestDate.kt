package com.mindera.rocketscience.spacex_presentation

import com.mindera.rocketscience.spacex_domain.model.CompanyInfo
import com.mindera.rocketscience.spacex_domain.model.SpaceXLaunchItem
import com.mindera.rocketscience.util.UiText

object TestDate {
    val getCompanyInfo = CompanyInfo(
        name = "Gavin Maddox",
        founder = "eros",
        founded = 4487,
        employees = 1874,
        launchSites = 1194,
        valuation = 8932
    )

    val spaceXItems = SpaceXLaunchItem(
        id = 7196,
        missionName = "Bethany Bentley",
        date = UiText.DynamicString(""),
        year = "gravida",
        time = UiText.DynamicString(""),
        rocket = "maximus",
        imageLink = null,
        success = false,
        daysSinceLaunch = 7646,
        wikipediaLink = null,
        articleLink = null
    )
}