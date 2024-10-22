package com.mindera.rocketscience.spacex_data.mapper

import com.mindera.rocketscience.spacex_data.local.entity.SpaceXLaunchItemEntity
import com.mindera.rocketscience.spacex_data.remote.dto.AllSpaceXLaunchesResponseItem
import com.mindera.rocketscience.spacex_data.remote.dto.CompanyInfoResponse
import com.mindera.rocketscience.spacex_domain.model.CompanyInfo
import com.mindera.rocketscience.spacex_domain.model.SpaceXLaunchItem
import com.mindera.rocketscience.util.daysFromToday
import com.mindera.rocketscience.util.getDateWithoutTime
import com.mindera.rocketscience.util.getTimeFromDateString

fun CompanyInfoResponse.toCompanyInfo(): CompanyInfo = CompanyInfo(
    name = name,
    founder = founder,
    founded = founded,
    employees = employees,
    launchSites = launch_sites,
    valuation = valuation
)

fun AllSpaceXLaunchesResponseItem.toSpaceXLaunchItemEntity() = SpaceXLaunchItemEntity(
    missionName = name,
    date = date_local,
    rocket = rocket,
    imageLink = links.patch.small ?: "",
    rocketName = name,
    success = success ?: false,
    wikipediaLink = links.wikipedia,
    articleLink = links.article,
    year = date_local.take(4)
)

fun SpaceXLaunchItemEntity.toSpaceXLaunchItem() = SpaceXLaunchItem(
    missionName = missionName,
    date = getDateWithoutTime(date),
    rocket = rocket,
    imageLink = imageLink,
    id = id,
    success = success,
    wikipediaLink = wikipediaLink,
    articleLink = articleLink,
    year = year,
    time = getTimeFromDateString(date),
    daysSinceLaunch = daysFromToday(date)
)