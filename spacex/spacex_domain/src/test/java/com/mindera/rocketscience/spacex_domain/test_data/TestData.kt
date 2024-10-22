package com.mindera.rocketscience.spacex_domain.test_data

import com.mindera.rocketscience.spacex_domain.model.CompanyInfo
import com.mindera.rocketscience.spacex_domain.model.SpaceXLaunchItem
import com.mindera.rocketscience.util.UiText

object TestData {
    val launchItem = SpaceXLaunchItem(
        id = 4462,
        missionName = "Crew-6",
        date = UiText.DynamicString("March 2, 2023"),
        year = "2023",
        time = UiText.DynamicString("1:45 AM"),
        rocket = "Falcon 9",
        imageLink = "https://example.com/image.jpg",
        success = true,
        daysSinceLaunch = 1224,
        wikipediaLink = "https://en.wikipedia.org/wiki/Crew-6",
        articleLink = "https://example.com/article.html"
    )

    val companyInfo = CompanyInfo(
        name = "Patrice Mayo",
        founder = "Elon musk",
        founded = 5025,
        employees = 4311,
        launchSites = 4033,
        valuation = 4683

    )
}